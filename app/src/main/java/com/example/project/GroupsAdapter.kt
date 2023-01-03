package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FeedItemBinding
import com.example.project.databinding.GroupItemBinding
import com.example.project.models.Flag
import com.example.project.models.Group
import com.example.project.models.Post

class GroupsAdapter(private var dataSource: List<Group>, val onItemClick: OnItemClick):
    RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_item, parent, false)
        return ViewHolder(GroupItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.groupName.text = dataSource[position].groupName
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ViewHolder(binding: GroupItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val groupName: TextView = binding.groupName
    }

}
