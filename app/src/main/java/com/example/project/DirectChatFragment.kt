package com.example.project

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentDirectChatBinding
import com.example.project.models.Chat
import com.example.project.models.ChatStatus
import java.util.*

class DirectChatFragment : Fragment() {

    private lateinit var binding: FragmentDirectChatBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val args: DirectChatFragmentArgs by navArgs()
    private val chatsAdapter: DirectChatAdapter by lazy {
        DirectChatAdapter(
            getLatestChatsPerUser(),
            viewModel.loggedInUser.username
        )
    }
    private lateinit var chats: List<Chat>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDirectChatBinding.inflate(inflater, container, false)
        initClickListeners()
        initRecyclerView()
        chats = getLatestChatsPerUser()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.chatsRecyclerView.apply {
            adapter = chatsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun getLatestChatsPerUser(): MutableList<Chat> {
        return viewModel.chatsFlow.value.filter{ chat ->
            (chat.usernameFrom == viewModel.loggedInUser.username && chat.usernameTo == args.username) ||
                    (chat.usernameTo == viewModel.loggedInUser.username && chat.usernameFrom == args.username) }.sortedByDescending{ it.dateSend }.toMutableList()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postCommentButton.setOnClickListener{
            val comment = binding.messageInputText.text.toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val currentDateAndTime = sdf.format(Date())
            val chat = Chat(viewModel.chats.size + 1, 1, args.username, viewModel.loggedInUser.username, comment, currentDateAndTime, ChatStatus.SEND)
            viewModel.addChat(chat)
            binding.messageInputText.text.clear()
            chats = chats + chat
            chatsAdapter.notifyItemInserted(chats.size + 1)
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}