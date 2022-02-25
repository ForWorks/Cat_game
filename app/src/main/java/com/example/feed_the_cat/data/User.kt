package com.example.feed_the_cat.data

import java.io.Serializable

data class User(var login: String, var list: MutableList<Pair>): Serializable {
    constructor(): this("", mutableListOf())
}

data class Pair(var points: Int, var date: String): Serializable {
    constructor(): this(0,"")
}