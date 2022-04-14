package com.tiorisnanto.myapplication.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tiorisnanto.myapplication.R

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        supportActionBar?.hide()
    }
}