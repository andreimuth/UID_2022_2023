package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FeedItemBinding
import com.example.project.models.Post

class HomeFeedAdapter(private val dataSource: List<Post>, val onItemClick: OnItemClick):
    RecyclerView.Adapter<HomeFeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_item, parent, false)
        return ViewHolder(FeedItemBinding.bind(view))
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

    class ViewHolder(binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val userIcon: ImageView = binding.userIcon
        val username: TextView = binding.username
        val date: TextView = binding.date
        val postText: TextView = binding.text
        val moreButton: ImageView = binding.moreButton
    }

}

interface OnItemClick {
    fun onItemClick(position:Int, v: View)
    fun onItemLongClick(position:Int, v: View)
}