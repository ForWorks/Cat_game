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
import com.example.feed_the_cat.adapters.RecyclerAdapter
import com.example.feed_the_cat.data.User
import com.example.feed_the_cat.utils.Constants
import com.example.feed_the_cat.utils.Constants.Companion.DATE_FORMAT
import com.example.feed_the_cat.utils.Constants.Companion.SELECT
import com.example.feed_the_cat.utils.Constants.Companion.SEND_TYPE
import java.util.*

class Controller(private val context: Context) {

    fun makeAdapter(user: User): RecyclerAdapter {
        val adapter = RecyclerAdapter(user) {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = SEND_TYPE
            val body = "My result in the #FeedTheCat game is $it point(s)! Can you beat my record? https://www.google.com"
            sendIntent.putExtra(Intent.EXTRA_TEXT, body)
            context.startActivity(Intent.createChooser(sendIntent, SELECT))
        }
        return adapter
    }

    fun createAnimation(resId: Int): Animation {
        val catAnimation = AnimationUtils.loadAnimation(context, resId)
        catAnimation.repeatMode = Animation.REVERSE
        catAnimation.duration = 1500
        return catAnimation
    }

    fun createAboutDialog(resId: Int): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setCancelable(true)
            .setView(resId)
        return builder.create()
    }

    fun createResultDialog(adapter: RecyclerAdapter): AlertDialog {
        val recyclerView = RecyclerView(context)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val builder = AlertDialog.Builder(context)
            .setCancelable(true)
            .setView(recyclerView)
            .setTitle(SELECT)
        return builder.create()
    }

    @SuppressLint("SimpleDateFormat")
    fun dateConvert(): String {
        return SimpleDateFormat(DATE_FORMAT).format(Date().time).toString()
    }

    fun getIntentData(intent: Intent): String {
        return intent.getStringExtra(Constants.USER_HASH)!!
    }
}