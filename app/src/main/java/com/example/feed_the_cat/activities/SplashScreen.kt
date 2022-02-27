package com.example.feed_the_cat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.feed_the_cat.R
import com.example.feed_the_cat.controllers.Controller
import com.example.feed_the_cat.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.mainCat.animation = Controller(this).createAnimation(R.anim.splash_anim)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AuthorizationPage::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}