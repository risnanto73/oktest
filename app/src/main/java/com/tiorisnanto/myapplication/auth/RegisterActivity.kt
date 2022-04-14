package com.tiorisnanto.myapplication.auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tiorisnanto.myapplication.MainActivity
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private var email = ""
    private var password = ""
    private var displayName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ProgressBar
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //Init firebase Auth
        auth = FirebaseAuth.getInstance()

        //handle klik begin sign up
        binding.btnSignUp.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        email = binding.edtEmail.text.toString().trim()
        password = binding.edtPassword.text.toString().trim()
        displayName = binding.edtName.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Invalid email format"
        } else if (TextUtils.isEmpty(displayName)){
            binding.edtName.error = "Please enter Name"
        }else if (TextUtils.isEmpty(password)) {
            binding.edtPassword.error = "Please enter password"
        } else if (password.length < 6) {
            binding.edtPassword.error = "Password must atlesat 6 characters long"
        } else {
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                val firebaseUser = auth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(
                    this,
                    "Account Created with email ${email} ",
                    Toast.LENGTH_SHORT
                )
                    .show()

                //open profile
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                //signup failure
                progressDialog.dismiss()
                Toast.makeText(this, "Sign Up failed due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}