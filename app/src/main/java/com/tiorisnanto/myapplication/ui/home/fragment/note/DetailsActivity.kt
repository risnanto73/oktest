package com.tiorisnanto.myapplication.ui.home.fragment.note

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.tiorisnanto.myapplication.R
import kotlinx.android.synthetic.main.activity_details.*
import java.io.ByteArrayOutputStream
import java.time.Clock

class DetailsActivity : AppCompatActivity() {

    private val dbHandler = DBHelper(this, null)
    lateinit var nameEditText: EditText
    lateinit var ageEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var dateTex: TextClock
    lateinit var modifyId: String
    lateinit var imgCoder : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        nameEditText = findViewById(R.id.name)
        ageEditText = findViewById(R.id.age)
        emailEditText = findViewById(R.id.email)
        dateTex = findViewById(R.id.date)
        imgCoder = findViewById(R.id.imgQrCode)

        /* Check  if activity opened from List Item Click */
        if (intent.hasExtra("id")) {
            modifyId = intent.getStringExtra("id")!!
            nameEditText.setText(intent.getStringExtra("name"))
            ageEditText.setText(intent.getStringExtra("age"))
            emailEditText.setText(intent.getStringExtra("email"))
            dateTex

            findViewById<Button>(R.id.btnAdd).visibility = View.GONE
        } else {
            findViewById<Button>(R.id.btnUpdate).visibility = View.GONE
            findViewById<Button>(R.id.btnDelete).visibility = View.GONE
            findViewById<Button>(R.id.btnPrint).visibility = View.GONE
        }
    }

    fun add(v: View) {
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString()
        val email = emailEditText.text.toString()
        val date = dateTex.text.toString()


        if (name.isEmpty() || age.isEmpty() || email.isEmpty()) {
            nameEditText.error = "Please enter name"
            ageEditText.error = "Please enter age"
            emailEditText.error = "Please enter email"
        }

    }

    fun update(v: View) {
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString()
        val email = emailEditText.text.toString()
        val date = dateTex.text.toString()
        dbHandler.updateRow(modifyId, name, age, email, date)
        Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun delete(v: View) {
        dbHandler.deleteRow(modifyId)
        Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show()
        finish()
    }
}