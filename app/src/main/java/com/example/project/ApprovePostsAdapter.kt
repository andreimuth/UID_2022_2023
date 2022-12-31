package com.example.project

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ApprovePostItemBinding
import com.example.project.models.Flag
import com.example.project.models.Post

class ApprovePostsAdapter(private var dataSource: List<Post>, val onItemClick: OnItemClick):
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
        holder.menuButton.setOnClickListener{
            onItemClick.onItemClick(position, holder.itemView,it)
        }

        if(dataSource[position].flag != Flag.NONE) {
            if(dataSource[position].flag == Flag.IMPORTANT)
                holder.flagIcon.setImageResource(R.drawable.ic_important)
            else
                holder.flagIcon.setImageResource(R.drawable.ic_trivial)
        }
    }

    fun submitList(posts: List<Post>) {
        dataSource = posts
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ViewHolder(binding: ApprovePostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val userIcon: ImageView = binding.userIcon
        val username: TextView = binding.username
        val date: TextView = binding.date
        val postText: TextView = binding.text
        val menuButton: ImageView = binding.menuButton
        val flagIcon: ImageView = binding.flagIcon
    }

}