package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentGroupFeedBinding
import com.example.project.models.PostType

class GroupFeedFragment: Fragment(), OnItemClick, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: FragmentGroupFeedBinding
    private val viewModel: SharedViewModel by viewModels()
    private val groupFeedAdapter: HomeFeedAdapter by lazy { HomeFeedAdapter(viewModel.postsFlow.value, this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupFeedBinding.inflate(inflater, container, false)
        initClickListeners()
        initRecyclerView()
        return binding.root;
    }

    private fun initRecyclerView() {
        binding.homeRecyclerView.apply {
            adapter = groupFeedAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,true)
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }
        binding.addPostButton.setOnClickListener{
            findNavController().navigate(GroupFeedFragmentDirections.actionGroupFeedToNewPost())
        }

        binding.filterButton.setOnClickListener{
            showMenu(it)
        }

        binding.searchBar.setOnQueryTextFocusChangeListener { _ , hasFocus ->
            if (hasFocus) {
                binding.addPostButton.visibility= View.GONE
                binding.filterButton.visibility= View.GONE
            } else {
                binding.addPostButton.visibility= View.VISIBLE
                binding.filterButton.visibility= View.VISIBLE
            }
        }
    }

    private fun showMenu(v: View) {
        PopupMenu(context, v).apply {
            setOnMenuItemClickListener(this@GroupFeedFragment)
            inflate(R.menu.filter_menu)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.posts_item -> {
                viewModel.filterPosts(PostType.POST)
                groupFeedAdapter.notifyDataSetChanged()
                Log.d("MYTAG",viewModel.postsFlow.value.toString())
                true
            }
            R.id.announcements_item -> {
                viewModel.filterPosts(PostType.ANNOUNCEMENT)
                groupFeedAdapter.notifyDataSetChanged()
                true
            }
            else -> false
        }
    }


    override fun onItemClick(position: Int, v: View) {
        val selectedPost = viewModel.feedPosts[position]
        findNavController().navigate(GroupFeedFragmentDirections.actionGroupFeedToPostDetails(selectedPost.id.toString()))
    }

    override fun onItemLongClick(position: Int, v: View) {
        TODO("Not yet implemented")
    }

}