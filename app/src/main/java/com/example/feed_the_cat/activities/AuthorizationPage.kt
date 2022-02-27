package com.example.feed_the_cat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.feed_the_cat.data.DBReference
import com.example.feed_the_cat.data.User
import com.example.feed_the_cat.databinding.ActivityAuthorizationPageBinding
import com.example.feed_the_cat.utils.Constants.Companion.FAILED
import com.example.feed_the_cat.utils.Constants.Companion.USER_HASH
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class AuthorizationPage : AppCompatActivity() {

    private val binding by lazy { ActivityAuthorizationPageBinding.inflate(layoutInflater) }
    private val reference by lazy { DBReference.getReference() }
    private val auth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() {
        binding.regBtn.setOnClickListener {
            val login = binding.loginEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            if(checkData(login, password)) {
                auth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        reference.child(login.hashCode().toString()).setValue(User(login, mutableListOf()))
                        createNewPage(login)
                    } else
                        Toast.makeText(this, FAILED, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.authBtn.setOnClickListener {
            val login = binding.loginEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            if(checkData(login, password)) {
                auth.signInWithEmailAndPassword(login, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        createNewPage(login)
                    } else
                        Toast.makeText(this, FAILED, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkData(login: String, password: String): Boolean {
        if(login.isNotEmpty() && password.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(login).matches())
            return true
        else
            Toast.makeText(this, FAILED, Toast.LENGTH_SHORT).show()
        return false
    }

    private fun createNewPage(login: String) {
        val intent = Intent(this, GamePage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra(USER_HASH, login.hashCode().toString())
        startActivity(intent)
    }
}
