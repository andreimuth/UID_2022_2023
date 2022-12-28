package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentHomeBinding

class HomeFragment: Fragment(), OnItemClick {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.addPostButton.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeToNewPost())
        }
        initRecyclerView()
        return binding.root;
    }


    private fun initRecyclerView() {
        binding.homeRecyclerView.apply {
            adapter = HomeFeedAdapter(viewModel.feedPosts,this@HomeFragment)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,true)
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