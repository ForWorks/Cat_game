package com.example.feed_the_cat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.feed_the_cat.data.DBReference
import com.example.feed_the_cat.data.Pair
import com.example.feed_the_cat.R
import com.example.feed_the_cat.data.User
import com.example.feed_the_cat.controllers.Controller
import com.example.feed_the_cat.databinding.ActivityGamePageBinding
import com.example.feed_the_cat.utils.Constants.Companion.DATABASE_CHILD
import com.example.feed_the_cat.utils.Constants.Companion.SAVED
import com.google.firebase.database.*

class GamePage : AppCompatActivity() {

    private val binding by lazy { ActivityGamePageBinding.inflate(layoutInflater) }
    private val controller by lazy { Controller(this) }
    private val aboutDialog by lazy { controller.createAboutDialog(R.layout.about_layout) }
    private val reference by lazy { DBReference.getReference() }
    private val user by lazy { User() }
    private val adapter by lazy { controller.makeAdapter(user) }
    private val resultDialog by lazy { controller.createResultDialog(adapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getUser(controller.getIntentData(intent))
        setListeners()
    }

    private fun setListeners() {
        var clicksCount = 0
        binding.satietyCount.text = clicksCount.toString()

        binding.feedButton.setOnClickListener {
            clicksCount++
            if(clicksCount % 15 == 0)
                binding.catImage.startAnimation(controller.createAnimation(R.anim.cat_animation))
            binding.satietyCount.text = clicksCount.toString()
        }

        binding.saveButton.setOnClickListener {
            user.list.add(Pair(clicksCount, controller.dateConvert()))
            reference.child(controller.getIntentData(intent)).child(DATABASE_CHILD).setValue(user.list)
            Toast.makeText(this, SAVED, Toast.LENGTH_LONG).show()
        }
    }

    private fun getUser(hash: String) {
        reference.child(hash).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.getValue(User::class.java)!!
                user.list.clear()
                user.list.addAll(temp.list)
                user.login = temp.login
            }
            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                aboutDialog.show()
            }
            R.id.rules -> {
                val intent = Intent(this, RulesPage::class.java)
                startActivity(intent)
            }
            R.id.result -> {
                adapter.notifyDataSetChanged()
                resultDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

