package com.example.project

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentApprovePostsBinding

class ApprovePostsFragment: Fragment() ,OnItemClick{

    private lateinit var binding: FragmentApprovePostsBinding
    private val viewModel: SharedViewModel by viewModels()
    private val approvePostsAdapter: ApprovePostsAdapter by lazy { ApprovePostsAdapter(viewModel.postsFlow.value, this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApprovePostsBinding.inflate(inflater, container, false)
        initClickListeners()
        initRecyclerView()
        return binding.root;
    }

    private fun initRecyclerView() {
        binding.approvePostsRecyclerView.apply {
            adapter = approvePostsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,true)
            registerForContextMenu(this)
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.approve_posts_menu, menu)
    }

    override fun onItemClick(position: Int, v: View) {
        val selectedPost = viewModel.feedPosts[position]
        findNavController().navigate(ApprovePostsFragmentDirections.actionApprovePostsToPostDetails(selectedPost.id.toString()))
    }

    override fun onItemLongClick(position: Int, v: View) {
    }
}