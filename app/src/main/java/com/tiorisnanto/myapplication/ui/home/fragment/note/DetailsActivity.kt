package com.tiorisnanto.myapplication.ui.home.fragment.note

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.print.PrintHelper
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.tiorisnanto.myapplication.R
import kotlinx.android.synthetic.main.activity_details.*
import java.io.ByteArrayOutputStream
import java.text.NumberFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    private val dbHandler = DBHelper(this, null)
    lateinit var nameEditText: EditText
    lateinit var ageEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var dateTex: TextClock
    lateinit var modifyId: String
    lateinit var imgCoder: ImageView
    lateinit var btnPrintPDF: Button
    lateinit var btnPlusDewasa: Button
    lateinit var btnMinDewasa: Button
    lateinit var valueDewasa: TextView
    lateinit var textCountDewasa: TextView
    lateinit var textHargaDewasa: TextView
    lateinit var btnPlusAnak: Button
    lateinit var btnMinAnak: Button
    lateinit var valueAnak: TextView
    lateinit var textCountAnak: TextView
    lateinit var textHargaAnak: TextView
    lateinit var textCountTotal: TextView
    lateinit var textHargaTotal: TextView

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        nameEditText = findViewById(R.id.name)
        ageEditText = findViewById(R.id.age)
        emailEditText = findViewById(R.id.email)
        dateTex = findViewById(R.id.date)
        imgCoder = findViewById(R.id.imgQrCode)
        btnPrintPDF = findViewById(R.id.btnPrint)
        btnPlusDewasa = findViewById(R.id.btnDewasaPlus)
        btnMinDewasa = findViewById(R.id.btnDewasaMin)
        valueDewasa = findViewById(R.id.valueDewasa)
        textCountDewasa = findViewById(R.id.txtCountDewasa)
        textHargaDewasa = findViewById(R.id.txtHargaDewasa)
        btnPlusAnak = findViewById(R.id.btnAnakPlus)
        btnMinAnak = findViewById(R.id.btnAnakMin)
        valueAnak = findViewById(R.id.valueAnak)
        textCountAnak = findViewById(R.id.txtCountAnak)
        textHargaAnak = findViewById(R.id.txtHargaAnak)
        textCountTotal = findViewById(R.id.txtCountTotal)
        textHargaTotal = findViewById(R.id.txtHargaTotal)


        increaseInteger()

        decreaseInteger()

        /* Check  if activity opened from List Item Click */
        if (intent.hasExtra("id")) {
            modifyId = intent.getStringExtra("id")!!
            nameEditText.setText(intent.getStringExtra("name"))
            ageEditText.setText(intent.getStringExtra("age"))
            emailEditText.setText(intent.getStringExtra("email"))
            txtTime.setText(intent.getStringExtra("time"))
            dateTex.setText(intent.getStringExtra("date"))
            textCountTotal.setText(intent.getStringExtra("count"))
            textHargaTotal.setText(intent.getStringExtra("price"))
            val qrCodeWriter = QRCodeWriter()
            try {
                val bitMatrix = qrCodeWriter.encode(modifyId, BarcodeFormat.QR_CODE, 512, 512)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                    }
                }

                val stream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                printPDF(byteArray)

                imgCoder.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))

                val bundle = Bundle()
                bundle.putByteArray("qr_code", byteArray)
                val printIntent = Intent(this, DataActivity::class.java)
                printIntent.putExtras(bundle)

            } catch (e: WriterException) {
                e.printStackTrace()
            }

            findViewById<Button>(R.id.btnAdd).visibility = View.GONE
        } else {
            findViewById<Button>(R.id.btnUpdate).visibility = View.GONE
            findViewById<Button>(R.id.btnDelete).visibility = View.GONE
            findViewById<Button>(R.id.btnPrint).visibility = View.GONE
            tilTime.visibility = View.GONE
            txtTime.visibility = View.GONE
        }

        imgCoder.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString()
            val email = emailEditText.text.toString()
            val date = txtTime.text.toString()
            Toast.makeText(
                this,
                "Nama " + "${name}" + "\n" + "Umur " + "${age}" + "\n" + "Email " + "${email}" + "\n" + "Tanggal " + "${date}",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun decreaseInteger() {
        btnMinDewasa.setOnClickListener {
            val hargaDewasa = 10000
            val hargaAnak = 5000
            if (valueDewasa.text.toString().toInt() > 0) {
                valueDewasa.text = (valueDewasa.text.toString().toInt() - 1).toString()
                textCountDewasa.text = valueDewasa.text.toString()
                textHargaDewasa.text =
                    (valueDewasa.text.toString().toInt() * hargaDewasa).toString()
                textCountTotal.text =
                    (valueDewasa.text.toString().toInt() + valueAnak.text.toString()
                        .toInt()).toString()
                textHargaTotal.text =
                    (valueDewasa.text.toString().toInt() * hargaDewasa + valueAnak.text.toString()
                        .toInt() * hargaAnak).toString()
            }
        }

        btnMinAnak.setOnClickListener {
            val hargaAnak = 5000
            val hargaDewasa = 10000
            if (valueAnak.text.toString().toInt() > 0) {
                valueAnak.text = (valueAnak.text.toString().toInt() - 1).toString()
                textCountAnak.text = valueAnak.text.toString()
                textHargaAnak.text = (valueAnak.text.toString().toInt() * hargaAnak).toString()
                txtCountTotal.text =
                    (valueDewasa.text.toString().toInt() + valueAnak.text.toString()
                        .toInt()).toString()
                textHargaTotal.text =
                    (valueDewasa.text.toString().toInt() * hargaDewasa + valueAnak.text.toString()
                        .toInt() * hargaAnak).toString()
            }
        }

    }

    private fun increaseInteger() {
        btnPlusDewasa.setOnClickListener {
            val hargaDewasa = 10000
            val hargaAnak = 5000
            if (valueDewasa.text.toString().toInt() >= 0) {
                valueDewasa.text = (valueDewasa.text.toString().toInt() + 1).toString()
                textCountDewasa.text = valueDewasa.text.toString()
                textHargaDewasa.text =
                    (valueDewasa.text.toString().toInt() * hargaDewasa).toString()
                textCountTotal.text =
                    (valueDewasa.text.toString().toInt() + valueAnak.text.toString()
                        .toInt()).toString()
                textHargaTotal.text =
                    (valueDewasa.text.toString().toInt() * hargaDewasa + valueAnak.text.toString()
                        .toInt() * hargaAnak).toString()
            }
        }
        btnPlusAnak.setOnClickListener {
            val hargaAnak = 5000
            val hargaDewasa = 10000
            if (valueAnak.text.toString().toInt() >= 0) {
                valueAnak.text = (valueAnak.text.toString().toInt() + 1).toString()
                textCountAnak.text = valueAnak.text.toString()
                textHargaAnak.text = (valueAnak.text.toString().toInt() * hargaAnak).toString()
                textCountTotal.text =
                    (valueDewasa.text.toString().toInt() + valueAnak.text.toString()
                        .toInt()).toString()
                textHargaTotal.text =
                    (valueDewasa.text.toString().toInt() * hargaDewasa + valueAnak.text.toString()
                        .toInt() * hargaAnak).toString()
            }
        }
    }

    private fun printPDF(byteArray: ByteArray) {
        btnPrintPDF.setOnClickListener {
            val printHelper = PrintHelper(this)
            printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            printHelper.printBitmap("wisata", bitmap)
        }

    }

    fun add(v: View) {
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString()
        val email = emailEditText.text.toString()
        val date = dateTex.text.toString()
        val count = textCountTotal.text.toString()
        val price = textHargaTotal.text.toString()

        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(date, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }

        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        imgCoder.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))

        val qrCode = byteArray

        if (name.isEmpty() || age.isEmpty() || email.isEmpty()) {
            nameEditText.error = "Please enter name"
            ageEditText.error = "Please enter age"
            emailEditText.error = "Please enter email"
        } else {
            dbHandler.insertRow(name, age, email, date, qrCode, count, price)
            Toast.makeText(this, "Data Addeded", Toast.LENGTH_SHORT).show()
            finish()
        }


    }

    fun update(v: View) {
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString()
        val email = emailEditText.text.toString()
        val date = dateTex.text.toString()
        val count = textCountTotal.text.toString()
        val price = textHargaTotal.text.toString()

        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(date, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }

        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        imgCoder.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))


        val qrCode = byteArray

        dbHandler.updateRow(modifyId, name, age, email, date, qrCode, count, price)
        Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun delete(v: View) {
        dbHandler.deleteRow(modifyId)
        Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun formatRupiah(number: Double): String? {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(number)
    }
}