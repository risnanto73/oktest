package com.tiorisnanto.myapplication.ui.home.fragment.note

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.tiorisnanto.myapplication.R
import java.io.ByteArrayOutputStream

class CustomAdapter(
    private val context: Context,
    private val dataList: ArrayList<HashMap<String, String>>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dataitem = dataList[position]

        val rowView = inflater.inflate(R.layout.list_row, parent, false)
        rowView.findViewById<TextView>(R.id.row_name).text = dataitem["name"]
        rowView.findViewById<TextView>(R.id.row_age).text = "Age: " + dataitem["age"]
        rowView.findViewById<TextView>(R.id.row_email).text = "Email: " + dataitem["email"]
        rowView.findViewById<TextView>(R.id.row_date).text = "Date: " + dataitem["date"]

        rowView.tag = position
        return rowView

//        val date = dataitem["date"]
//        val imgCoder = rowView.findViewById<ImageView>(R.id.row_image)
//        val qrCodeWriter = QRCodeWriter()
//        val bitMatrix = qrCodeWriter.encode(date, BarcodeFormat.QR_CODE, 512, 512)
//        val width = bitMatrix.width
//        val height = bitMatrix.height
//        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
//
//        for (x in 0 until width) {
//            for (y in 0 until height) {
//                bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
//            }
//        }
//
//        val stream = ByteArrayOutputStream()
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        val byteArray = stream.toByteArray()
//
//        imgCoder.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
//
//        val qrCode = byteArray
//
//        Glide.with(context)
//            .load(qrCode)
//            .into(imgCoder)
    }
}