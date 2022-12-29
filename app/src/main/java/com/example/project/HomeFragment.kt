package com.example.project

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentHomeBinding


class HomeFragment: Fragment(), OnItemClick,PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initClickListeners()
        initRecyclerView()
        return binding.root;
    }

    private fun initRecyclerView() {
        binding.homeRecyclerView.apply {
            adapter = HomeFeedAdapter(viewModel.postsFlow.value,this@HomeFragment)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,true)
        }
    }

    private fun initClickListeners() {
        binding.addPostButton.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeToNewPost())
        }

        binding.filterButton.setOnClickListener{
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
                true
            }
            R.id.announcements_item -> {
                true
            }
            else -> false
        }
    }

    override fun onItemClick(position: Int, v: View) {
        val selectedPost = viewModel.feedPosts[position]
        findNavController().navigate(HomeFragmentDirections.actionHomeToPostDetails(selectedPost.id.toString()))
    }

    override fun onItemLongClick(position: Int, v: View) {
        TODO("Not yet implemented")
    }


}