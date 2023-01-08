package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.UserItemBinding
import com.example.project.models.Group
import com.example.project.models.User

class UsersAdapter(private var dataSource: List<User>):
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return ViewHolder(UserItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
        val user = dataSource[position]
        if(user.isBanned) {
            holder.banIcon.visibility = View.GONE
            holder.removeBanIcon.visibility = View.VISIBLE
        } else {
            holder.banIcon.visibility = View.VISIBLE
            holder.removeBanIcon.visibility = View.GONE
        }

        holder.username.text = dataSource[position].username
        holder.banIcon.setOnClickListener {
            banUser(position, user, holder)
        }
        holder.removeBanIcon.setOnClickListener {
            removeBan(position, user, holder)
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun refreshData(newData: List<User>) {
        dataSource = newData
        notifyDataSetChanged()
    }

    private fun banUser(position: Int, user: User, holder: ViewHolder) {
        AlertDialog.Builder(holder.itemView.context)
            .setTitle("Ban user")
            .setMessage("Are you sure you want to ban ${user.username}?")
            .setPositiveButton("Yes") { _, _ ->
                user.isBanned = true
                notifyDataSetChanged()
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }

    private fun removeBan(position: Int, user: User, holder: ViewHolder) {
        AlertDialog.Builder(holder.itemView.context)
            .setTitle("Remove ban")
            .setMessage("Are you sure you want to remove ban from ${user.username}?")
            .setPositiveButton("Yes") { _, _ ->
                user.isBanned = false
                notifyDataSetChanged()
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }

    class ViewHolder(binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val username: TextView = binding.username
        val banIcon: ImageView = binding.banIcon
        val removeBanIcon: ImageView = binding.removeBan
    }
}