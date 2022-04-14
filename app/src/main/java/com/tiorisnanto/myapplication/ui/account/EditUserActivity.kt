package com.tiorisnanto.myapplication.ui.account

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.databinding.ActivityEditUserBinding
import java.io.ByteArrayOutputStream

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var imgUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar!!
        actionBar.setTitle("Edit Profile")
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            if (user.photoUrl != null) {
                Picasso.get().load(user.photoUrl).into(binding.imgProfile)
            } else {
                Picasso.get().load(R.drawable.profile).into(binding.imgProfile)
            }
            binding.edtNama.setText(user.displayName)
            binding.edtEmail.setText(user.email)

            if (user.isEmailVerified) {
                binding.imgVeif.visibility = View.VISIBLE
            } else {
                binding.imgVeif.visibility = View.GONE
            }
        }

        binding.imgProfile.setOnClickListener {
            openCamera()
        }

        binding.btnSave.setOnClickListener saveProfile@{

            val image = when {
                ::imgUri.isInitialized -> imgUri
                user?.photoUrl == null -> Uri.parse("https://th.bing.com/th/id/OIP.V8xz9mvOPDDcswCRqWOdlQAAAA?pid=ImgDet&rs=1")
                else -> user?.photoUrl
            }

            val name = binding.edtNama.text.toString()
            if (name.isEmpty()) {
                binding.edtNama.error = "Nama belum diisi!"
                binding.edtNama.requestFocus()
                return@saveProfile
            }

            //Update disini
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image as Uri?)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener { Task ->
                        if (Task.isSuccessful) {
                            val toast = Toast.makeText(
                                this,
                                "Data profile berhasil disimpan!",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                            onBackPressed()
                        } else {
                            Toast.makeText(
                                this,
                                "${Task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }

        binding.btnChangeEmail.setOnClickListener {
            changeEmail()
        }
    }

    private fun changeEmail() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val email = binding.edtEmail.text.toString()

        if (email.isEmpty()) {
            binding.edtEmail.error = "Email belum diisi!"
            binding.edtEmail.requestFocus()
            return
        }

        binding.cardVerifyPassword.visibility = View.VISIBLE

        binding.btnCancel.setOnClickListener {
            binding.cardVerifyPassword.visibility = View.GONE
        }

        binding.btnOtorisasiPass.setOnClickListener {
            val pass = binding.edtPassword.text.toString()
            if (pass.isEmpty()) {
                binding.edtPassword.error = "Password belum diisi!"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }

            user.let {
                val credential = EmailAuthProvider.getCredential(it?.email!!, pass)
                it?.reauthenticate(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user?.updateEmail(email)?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Email berhasil diubah", Toast.LENGTH_SHORT).show()
                                binding.cardVerifyPassword.visibility = View.GONE
                            } else {
                                Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

//            user.let {
//                it?.reauthenticate(EmailAuthProvider.getCredential(it?.email!!, pass)
//                )?.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        user?.updateEmail(email)?.addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                Toast.makeText(this, "Email berhasil diubah", Toast.LENGTH_SHORT).show()
//                                binding.cardVerifyPassword.visibility = View.GONE
//                            } else {
//                                Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    } else {
//                        Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            this.packageManager?.let {
                intent?.resolveActivity(it).also {
                    startActivityForResult(intent, 200)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImageToFirebase(imgBitmap)
        }
    }

    private fun uploadImageToFirebase(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.uid}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val img = baos.toByteArray()
        ref.putBytes(img)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener { Task ->
                        Task.result?.let { Uri ->
                            imgUri = Uri
                            binding.imgProfile.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        super.onRestart()
        binding
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    override fun onStart() {
        super.onStart()
        binding
    }

}