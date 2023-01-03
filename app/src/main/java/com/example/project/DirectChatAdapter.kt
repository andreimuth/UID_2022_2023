package com.example.project

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.DirectChatItem1Binding
import com.example.project.models.Chat

class DirectChatAdapter(private val dataSource: List<Chat>, private val usernameLoggedIn: String) :
    RecyclerView.Adapter<DirectChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = R.layout.direct_chat_item_1
        if(viewType == 2) {
            layout = R.layout.direct_chat_item_2
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)

        return ViewHolder(DirectChatItem1Binding.bind(view))
    }

    override fun getItemViewType(position: Int): Int {
        return if(dataSource[position].usernameFrom == usernameLoggedIn) {
            1
        } else {
            2
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text =  dataSource[position].text
        holder.date.text = dataSource[position].dateSend
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ViewHolder(binding: DirectChatItem1Binding) : RecyclerView.ViewHolder(binding.root) {
        val userIcon: ImageView = binding.userIcon
        val date: TextView = binding.date
        val text: TextView = binding.text
    }
}