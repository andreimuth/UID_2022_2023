package com.example.project

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.GroupItemBinding
import com.example.project.models.Group

class GroupsAdapter(private var dataSource: List<Group>, val onItemClick: OnItemClick, val viewModel: SharedViewModel):
    RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_item, parent, false)
        return ViewHolder(GroupItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.groupName.text = dataSource[position].groupName
        holder.groupDescription.text = dataSource[position].groupDescription
        holder.itemView.setOnClickListener{
            onItemClick.onItemClick(position, it)
        }
        if(viewModel.isUserInGroup(dataSource[position])){
            holder.joinButton.visibility = Button.GONE
        } else {
            holder.joinButton.visibility = Button.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun refreshData(newData: List<Group>) {
        dataSource = newData
        notifyDataSetChanged()
    }

    class ViewHolder(binding: GroupItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val groupName: TextView = binding.groupName
        val groupDescription: TextView = binding.groupDescription
        val joinButton: Button = binding.joinButton
    }

}
