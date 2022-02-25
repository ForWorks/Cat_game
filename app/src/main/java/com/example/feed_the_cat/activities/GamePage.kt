package com.example.feed_the_cat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.feed_the_cat.data.DBReference
import com.example.feed_the_cat.data.Pair
import com.example.feed_the_cat.R
import com.example.feed_the_cat.data.User
import com.example.feed_the_cat.adapters.RecyclerAdapter
import com.example.feed_the_cat.controllers.Controller
import com.example.feed_the_cat.databinding.ActivityGamePageBinding
import com.example.feed_the_cat.utils.Constants.Companion.DATABASE_CHILD
import com.google.firebase.database.*

class GamePage : AppCompatActivity() {

    private lateinit var binding: ActivityGamePageBinding
    private lateinit var controller: Controller
    private lateinit var dialog: AlertDialog
    private lateinit var reference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = Controller(this)
        recyclerView = RecyclerView(this)
        reference = DBReference.getReference()
        dialog = controller.createAlertdialog(recyclerView)

        init()
        getUser(controller.getIntentData(intent))
        setListeners()
    }

    private fun init() {
        user = User()
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        adapter = RecyclerAdapter(user) {
            val body = "My result in the FeedTheCat game is $it point(s)! Can you beat my record?"
            sendIntent.putExtra(Intent.EXTRA_TEXT, body)
            startActivity(Intent.createChooser(sendIntent, "Select"))
        }
        recyclerView.adapter = adapter
    }

    private fun setListeners() {
        var clicksCount = 0
        binding.satietyCount.text = clicksCount.toString()

        binding.feedButton.setOnClickListener {
            clicksCount++
            if(clicksCount % 15 == 0)
                binding.catImage.startAnimation(controller.createAnimation())
            binding.satietyCount.text = clicksCount.toString()
        }

        binding.saveButton.setOnClickListener {
            user.list.add(Pair(clicksCount, controller.dateConvert()))
            reference.child(controller.getIntentData(intent)).child(DATABASE_CHILD).setValue(user.list)
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_LONG).show()
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
                ////
            }
            R.id.result -> {
                adapter.notifyDataSetChanged()
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

