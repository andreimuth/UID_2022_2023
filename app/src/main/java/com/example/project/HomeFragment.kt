package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentHomeBinding
import com.example.project.models.PostType
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), OnItemClick, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val homeFeedAdapter: HomeFeedAdapter by lazy {
        HomeFeedAdapter(
            viewModel.postsFlow.value,
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initClickListeners()
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.homeRecyclerView.apply {
            adapter = homeFeedAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initClickListeners() {
        binding.addPostButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToNewPost())
        }

        binding.filterButton.setOnClickListener {
            showMenu(it)
        }

        binding.searchBar.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.addPostButton.visibility = View.GONE
                binding.filterButton.visibility = View.GONE
            } else {
                binding.addPostButton.visibility = View.VISIBLE
                binding.filterButton.visibility = View.VISIBLE
            }
        }

        lifecycleScope.launch {
            viewModel.postsFlow.collect { posts ->
                homeFeedAdapter.submitList(posts)
            }
        }

        binding.searchBar.setOnQueryTextListener(object : OnQueryTextListener {
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

    private fun showMenu(v: View) {
        PopupMenu(context, v).apply {
            setOnMenuItemClickListener(this@HomeFragment)
            inflate(R.menu.filter_menu)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.posts_item -> {
                viewModel.filterPosts(PostType.POST)
                true
            }
            R.id.announcements_item -> {
                viewModel.filterPosts(PostType.ANNOUNCEMENT)
                true
            }
            else -> false
        }
    }

    override fun onItemClick(position: Int, v: View,v2: View?) {
        val selectedPost = viewModel.feedPosts[position]
        findNavController().navigate(HomeFragmentDirections.actionHomeToPostDetails(selectedPost.id.toString()))
    }

    override fun onItemLongClick(position: Int, v: View) {
        TODO("Not yet implemented")
    }


}