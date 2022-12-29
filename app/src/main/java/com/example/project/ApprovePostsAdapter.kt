package com.example.project

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ApprovePostItemBinding
import com.example.project.models.Post

class ApprovePostsAdapter(private val dataSource: List<Post>, val onItemClick: OnItemClick):
    RecyclerView.Adapter<ApprovePostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.approve_post_item, parent, false)
        return ViewHolder(ApprovePostItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text = dataSource[position].username
        holder.date.text = dataSource[position].dateCreated
        holder.postText.text = dataSource[position].text
        holder.itemView.setOnClickListener{
            onItemClick.onItemClick(position, it)
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ViewHolder(binding: ApprovePostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val userIcon: ImageView = binding.userIcon
        val username: TextView = binding.username
        val date: TextView = binding.date
        val postText: TextView = binding.text
        val moreButton: ImageView = binding.moreButton
        val menuButton: ImageView = binding.menuButton
    }

}