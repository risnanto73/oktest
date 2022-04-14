package com.tiorisnanto.myapplication.onboarding.adapter.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.tiorisnanto.myapplication.MainActivity
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.auth.LoginActivity
import kotlinx.android.synthetic.main.fragment_first_screen.view.*
import kotlinx.android.synthetic.main.fragment_third_screen.view.*

class ThirdScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_third_screen, container, false)

        view.btnFinish.setOnClickListener {
            val intent = Intent(activity?.application, LoginActivity::class.java)
            startActivity(intent)
            onBoardingFinish()
        }

        return view
    }

    private fun onBoardingFinish(){
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("finished",true)
        editor.apply()
    }

}