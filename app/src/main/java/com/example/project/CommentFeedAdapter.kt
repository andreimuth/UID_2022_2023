package com.example.project

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.CommentItemBinding
import com.example.project.models.Comment

class CommentFeedAdapter(private val dataSource: List<Comment>) :
    RecyclerView.Adapter<CommentFeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        return ViewHolder(CommentItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text = dataSource[position].username
        holder.date.text = dataSource[position].dateCreated
        holder.commentText.text = dataSource[position].commentText
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ViewHolder(binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val userIcon: ImageView = binding.userIcon
        val username: TextView = binding.username
        val date: TextView = binding.date
        val commentText: TextView = binding.commentText
    }

}