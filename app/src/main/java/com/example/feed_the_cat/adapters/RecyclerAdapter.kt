package com.example.feed_the_cat.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feed_the_cat.R
import com.example.feed_the_cat.data.User
import com.example.feed_the_cat.databinding.RecyclerItemBinding

class RecyclerAdapter(private val user: User, private val listener: (Int) -> Unit): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(inflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val pair = user.list[position]
            points.text = "Points: ${pair.points}"
            date.text = pair.date
            root.setOnClickListener { listener(pair.points) }
        }
    }

    override fun getItemCount(): Int {
        return user.list.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
       val binding = RecyclerItemBinding.bind(itemView)
    }
}