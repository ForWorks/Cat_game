package com.example.feed_the_cat.controllers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feed_the_cat.R
import com.example.feed_the_cat.utils.Constants
import java.util.*

class Controller(private val context: Context) {

    fun createAnimation(): Animation {
        val catAnimation = AnimationUtils.loadAnimation(context, R.anim.cat_animation)
        catAnimation.repeatMode = Animation.REVERSE
        catAnimation.duration = 1500
        return catAnimation
    }

    fun createAlertdialog(recyclerView: RecyclerView): AlertDialog {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val builder = AlertDialog.Builder(context)
            .setCancelable(true)
            .setView(recyclerView)
            .setTitle("Select the result")
        return builder.create()
    }

    @SuppressLint("SimpleDateFormat")
    fun dateConvert(): String {
        return SimpleDateFormat("dd MM yyyy, HH:mm:ss").format(Date().time).toString()
    }

    fun getIntentData(intent: Intent): String {
        return intent.getStringExtra(Constants.USER_HASH)!!
    }
}