package com.tiorisnanto.myapplication.ui.home.fragment.dashboard

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.print.PrintHelper
import com.tiorisnanto.myapplication.databinding.ActivityDetailDashboardBinding


class DetailDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val byteArray = intent.getByteArrayExtra("qrCode")
        val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

        if (intent.extras != null) {
            val bundle = intent.extras
            binding.txtDate.text = bundle?.getString("date")
            bmp?.let { binding.imgQrCode.setImageBitmap(it) }
        } else {
            binding.txtDate.setText(intent.getStringExtra("date"))
            binding.imgQrCode.setImageResource(intent.getIntExtra("qrCode", 0))
        }

        binding.button.setOnClickListener {
            doPhotoPrint(bmp)
        }
    }

    private fun doPhotoPrint(bitmap: Bitmap) {
        val printHelper = PrintHelper(this)
        printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
        printHelper.printBitmap("printQrCode", bitmap)

    }


}
