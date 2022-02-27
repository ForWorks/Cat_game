package com.example.feed_the_cat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.feed_the_cat.R
import com.example.feed_the_cat.controllers.Controller
import com.example.feed_the_cat.databinding.ActivityRulesPageBinding

class RulesPage : AppCompatActivity() {

    private lateinit var binding: ActivityRulesPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRulesPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = Controller(this)
        binding.catImg.animation = controller.createAnimation(R.anim.cat_animation_2)
    }
}