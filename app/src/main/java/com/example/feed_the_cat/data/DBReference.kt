package com.example.feed_the_cat.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object DBReference {
    private var reference: DatabaseReference? = null
    fun getReference(): DatabaseReference {
        if(reference == null)
            return FirebaseDatabase.getInstance().reference
        return reference!!
    }
}