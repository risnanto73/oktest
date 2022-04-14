package com.tiorisnanto.myapplication.ui.home.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.squareup.picasso.Picasso
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.databinding.FragmentDashboardBinding
import com.tiorisnanto.myapplication.ui.home.fragment.dashboard.DetailDashboardActivity
import java.io.ByteArrayOutputStream


class DashboardFragment : Fragment() {

    private var binding: FragmentDashboardBinding? = null
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        val list = resources.getStringArray(R.array.List)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, list)
        binding!!.txtComplete.setAdapter(arrayAdapter)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProfile()

        binding!!.btnGenerate.setOnClickListener {
            val data = binding!!.txtDate.text.toString().trim()

            val qrCodeWriter = QRCodeWriter()
            try {
                val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512)
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

                binding!!.imgQrCode.setImageBitmap(bmp)

                Toast.makeText(activity, "Wisata Coban Putri Malang ${data}", Toast.LENGTH_SHORT)
                    .show()

                val bundle = Bundle()
                bundle.putString("date", data)
                val intent = Intent(activity, DetailDashboardActivity::class.java)
                intent.putExtra("qrCode", byteArray)
                intent.putExtras(bundle)
                startActivity(intent)

            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }

    }

    private fun showProfile() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            if (user.photoUrl != null) {
                Picasso.get().load(user.photoUrl).into(binding!!.imgProfile)
            } else {
                Picasso.get().load(R.drawable.profile).into(binding!!.imgProfile)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}
