package com.tiorisnanto.myapplication.auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tiorisnanto.myapplication.MainActivity
import com.tiorisnanto.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        //Konfigurasi Progess Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Loggin In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //Inisialisasi firebaseAuth
        auth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnLogin.setOnClickListener {
            validateData()
        }

        btnSignUp()

        binding.txtForgotPassword.setOnClickListener {
            forgotPassword()
        }
    }

    private fun forgotPassword() {
        binding.cardviewReset.visibility = View.VISIBLE

        binding.btnCancel.setOnClickListener {
            binding.cardviewReset.visibility = View.GONE
        }

        binding.btnResetPass.setOnClickListener {
            val edtEmailReset = binding.edtEmailReset.text.toString()

            if (edtEmailReset.isEmpty()){
                binding.edtEmailReset.error = "Email is required"
                binding.edtEmailReset.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(edtEmailReset).matches()){
                binding.edtEmailReset.error = "Email is invalid"
                binding.edtEmailReset.requestFocus()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(edtEmailReset)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                        binding.cardviewReset.visibility = View.GONE
                    }else{
                        Toast.makeText(this, "Failed to send email", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }


    private fun btnSignUp() {
        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun validateData() {
        email = binding.edtEmail.text.toString().trim()
        password = binding.edtPassword.text.toString().trim()

        //Validasi Data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Email Salah")
        } else if (TextUtils.isEmpty(password)) {
            //Tidak masukan Password
            binding.edtPassword.error = "Tolong Masukan Password"
        } else {
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //Login berhasil
                progressDialog.dismiss()
                //get User ingfo
                val firebaseUser = auth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Welcome back $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                //Login Gagal
                progressDialog.dismiss()
                Toast.makeText(this, "Login Gagal ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}