package com.example.project

import ChatsAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentChatsBinding
import com.example.project.models.Chat
import com.example.project.models.ChatStatus
import kotlinx.coroutines.launch

class ChatsFragment: Fragment(), OnItemClick {

    private lateinit var binding: FragmentChatsBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val chatsAdapter: ChatsAdapter by lazy {
        ChatsAdapter(
            viewModel.chatsFlow.value,
            viewModel.loggedInUser.username,
            this
        )
    }
    private lateinit var chats: List<Chat>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(inflater, container, false)
        viewModel.clearChats()
        chats = getLatestChatsPerUser()
        chats.forEach{ chat -> viewModel.addChatToFlow(chat) }
        initRecyclerView()
        initClickListeners()
        return binding.root
    }

    private fun getLatestChatsPerUser(): MutableList<Chat> {
        return viewModel.users.filter {user -> user.username != viewModel.loggedInUser.username}.map { user ->
            val userChat: List<Chat> = viewModel.chats.filter{ chat ->
                (chat.usernameFrom == viewModel.loggedInUser.username && chat.usernameTo == user.username) ||
                    (chat.usernameTo == viewModel.loggedInUser.username && chat.usernameFrom == user.username) }.sortedByDescending{ it.dateSend }
            if(userChat.size == 0) {
                Chat(-1, 1, viewModel.loggedInUser.username, user.username,"", "", ChatStatus.SEND)
            } else {
                userChat[0]
            }
        }.sortedByDescending { it.dateSend }.toMutableList()
    }

    private fun initRecyclerView() {
        binding.chatsRecyclerView.apply {
            adapter = chatsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initClickListeners() {
        lifecycleScope.launch {
            viewModel.chatsFlow.collect { chats ->
                chatsAdapter.submitList(chats)
            }
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null && p0 != "") {
                    viewModel.searchUserInChats(p0, chats)
                } else {
                    viewModel.clearChats()
                    chats = getLatestChatsPerUser()
                    chats.forEach{ chat -> viewModel.addChatToFlow(chat) }
                }
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null && p0 != "") {
                    viewModel.searchUserInChats(p0, chats)
                } else {
                    viewModel.clearChats()
                    chats = getLatestChatsPerUser()
                    chats.forEach{ chat -> viewModel.addChatToFlow(chat) }
                }
                return false
            }
        })
    }

    override fun onItemClick(position: Int, v: View,v2: View?) {
        val selectedChat = chats[position]
        var selectedUsername = selectedChat.usernameFrom
        if(selectedChat.usernameFrom == viewModel.loggedInUser.username) {
            selectedUsername = chats[position].usernameTo
        }
        findNavController().navigate(ChatsFragmentDirections.actionChatsToDirectChat(selectedUsername))
    }

    override fun onItemLongClick(position: Int, v: View) {
        TODO("Not yet implemented")
    }
}