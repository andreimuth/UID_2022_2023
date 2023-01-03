package com.example.project

import ChatsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentChatsBinding
import com.example.project.models.Chat
import com.example.project.models.ChatStatus

class ChatsFragment: Fragment(), OnItemClick {

    private lateinit var binding: FragmentChatsBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val chatsAdapter: ChatsAdapter by lazy {
        ChatsAdapter(
            getLatestChatsPerUser(),
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
        initRecyclerView()
        initClickListeners()
        chats = getLatestChatsPerUser()
        return binding.root
    }

    private fun getLatestChatsPerUser(): MutableList<Chat> {
        return viewModel.users.filter {user -> user.username != viewModel.loggedInUser.username}.map { user ->
            val userChat: List<Chat> = viewModel.chatsFlow.value.filter{ chat ->
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
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.filterByKeyword(p0)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.filterByKeyword(p0)
                }
                return false
            }
        })
    }

    override fun onItemClick(position: Int, v: View,v2: View?) {
        val selectedChat = chats[position]
        var selectedUsername = selectedChat.usernameFrom
        if(selectedChat.usernameFrom == viewModel.loggedInUser.username) {
            chats[position].usernameTo
        }
        findNavController().navigate(ChatsFragmentDirections.actionChatsToDirectChat(selectedUsername))
    }

    override fun onItemLongClick(position: Int, v: View) {
        TODO("Not yet implemented")
    }
}