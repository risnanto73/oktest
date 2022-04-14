package com.tiorisnanto.myapplication.ui.account

import android.app.AlertDialog
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isInvisible
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.squareup.picasso.Picasso
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.auth.LoginActivity
import com.tiorisnanto.myapplication.databinding.FragmentUserBinding
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_user, container, false)
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProfile()

        //Button Pindah Activity
        buttonEdit()

        //Button Logout
        btnLogout.setOnClickListener {
            buttonLogout()
        }

        //Button Verifikasi
        btnVerif.setOnClickListener {
            buttonVerif()
        }

        //Cek Verifikasi
        imgVerif.setOnClickListener {
            checkVerifikasi()
        }

        //Button Change Password
        btnChangePass.setOnClickListener {
            buttonChangePassword()
        }

    }

    private fun buttonChangePassword() {
        binding.cardVerifyPassword.visibility = View.VISIBLE

        binding.btnCancel.setOnClickListener {
            binding.cardVerifyPassword.visibility = View.GONE
        }

        binding.btnOtorisasiPass.setOnClickListener btnOtorisasi@ {
            val pass = binding.edtPassword.text.toString()

            auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            
            if (pass.isEmpty()) {
                binding.edtPassword.error = "Password is required"
                binding.edtPassword.requestFocus()
                return@btnOtorisasi
            }
            //Credential User
            user.let { 
                val credential = EmailAuthProvider.getCredential(user!!.email!!, pass)
                user.reauthenticate(credential)
                    .addOnCompleteListener { task ->
                        when{
                            task.isSuccessful -> {
                                binding.cardUpdatePassword.visibility = View.VISIBLE
                                binding.cardVerifyPassword.visibility = View.GONE
                            }
                            task.exception is FirebaseAuthInvalidCredentialsException -> {
                                binding.edtPassword.error = "Invalid Password"
                                binding.edtPassword.requestFocus()
                            }
                            else -> {
                                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                
                //Update Password
                binding.btnUpdatePassword.setOnClickListener {
                    val newPass = binding.edtNewPass.text.toString()
                    val confirmPass = binding.edtConfirmPass.text.toString()
                    
                    if (newPass.isEmpty()) {
                        binding.edtNewPass.error = "Password is required"
                        binding.edtNewPass.requestFocus()
                        return@setOnClickListener
                    }
                    
                    if (confirmPass.isEmpty()) {
                        binding.edtConfirmPass.error = "Password is required"
                        binding.edtConfirmPass.requestFocus()
                        return@setOnClickListener
                    }
                    
                    if (newPass.length <8) {
                        binding.edtNewPass.error = "Password must be at least 8 characters"
                        binding.edtNewPass.requestFocus()
                        return@setOnClickListener
                    }
                    if (confirmPass.length <8) {
                        binding.edtConfirmPass.error = "Password must be at least 8 characters"
                        binding.edtConfirmPass.requestFocus()
                        return@setOnClickListener
                    }
                    
                    if (newPass != confirmPass) {
                        binding.edtConfirmPass.error = "Password doesn't match"
                        binding.edtConfirmPass.requestFocus()
                        return@setOnClickListener
                    }
                    
                    user.let { 
                        it.updatePassword(newPass)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show()
                                    binding.cardUpdatePassword.visibility = View.GONE
                                    binding.cardVerifyPassword.visibility = View.GONE
                                    logoutAccount()
                                } else {
                                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                    
                    
                    
                }
            }

        }
    }

    private fun logoutAccount() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
        Toast.makeText(activity, "Silahkan Login Kembali", Toast.LENGTH_SHORT).show()
    }


    private fun checkVerifikasi() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user!!.isEmailVerified) {
            Toast.makeText(activity, "email-verifed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "nothink", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buttonVerif() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(activity, "Email verification send....", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buttonLogout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Androidly Alert")
        builder.setMessage("We have a message")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            val i = Intent(context, LoginActivity::class.java)
            startActivity(i)
            activity?.finish()
            Toast.makeText(
                activity,
                android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                activity,
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNeutralButton("Maybe") { dialog, which ->
            Toast.makeText(
                activity,
                "Maybe", Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
    }

    private fun buttonEdit() {
        binding.btnEdit.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            val intent = Intent(activity, EditUserActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    private fun showProfile() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {

            if (user.photoUrl != null) {
                Picasso.get().load(user.photoUrl).into(binding.imgProfile)
            } else {
                Picasso.get().load(R.drawable.profile).into(binding.imgProfile)
            }

            binding.edtEmail.setText(user.email)
            binding.edtNama.setText(user.displayName)

            if (user.isEmailVerified) {
                binding.btnVerif.visibility = View.GONE
                binding.imgVerif.visibility = View.VISIBLE
            } else {
                binding.btnVerif.visibility = View.VISIBLE
                binding.imgVerif.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }

    override fun onStart() {
        super.onStart()
        binding
    }

}