package com.example.project

import android.os.Bundle
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
import com.example.project.databinding.FragmentBanUsersBinding
import kotlinx.coroutines.launch

class BanUsersFragment: Fragment() {
    private lateinit var binding: FragmentBanUsersBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val banUsersAdapter: UsersAdapter by lazy {
        UsersAdapter(
            viewModel.usersFlow.value,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBanUsersBinding.inflate(inflater, container, false)
        initClickListeners()
        initRecyclerView()
        return binding.root;
    }

    private fun initRecyclerView() {
        binding.banPageRecyclerView.apply {
            adapter = banUsersAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        lifecycleScope.launch {
            viewModel.usersFlow.collect { users ->
                banUsersAdapter.refreshData(users)
            }
        }

        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    viewModel.searchUsers(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null) {
                    viewModel.searchUsers(newText)
                }
                return false
            }
        })
    }

}