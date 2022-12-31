package com.example.project

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentApprovePostsBinding
import com.example.project.models.Flag

class ApprovePostsFragment: Fragment() ,OnItemClick {

    private lateinit var binding: FragmentApprovePostsBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val approvePostsAdapter: ApprovePostsAdapter by lazy { ApprovePostsAdapter(viewModel.postsToApproveFlow.value, this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApprovePostsBinding.inflate(inflater, container, false)
        initClickListeners()
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.approvePostsRecyclerView.apply {
            adapter = approvePostsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            registerForContextMenu(this)
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(p0 != null) {
                    viewModel.filterPostsToApproveByKeyword(p0)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0 != null) {
                    viewModel.filterPostsToApproveByKeyword(p0)
                }
                return false
            }
        })
    }


    private fun showMenu(v: View,position: Int) {
        PopupMenu(context, v).apply {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.approve_post_item -> {
                        viewModel.approvePost(viewModel.postsToApproveFlow.value[position])
                        approvePostsAdapter.submitList(viewModel.postsToApproveFlow.value)
                        true
                    }
                    R.id.delete_post_item -> {
                        viewModel.removePost(viewModel.postsToApproveFlow.value[position])
                        approvePostsAdapter.submitList(viewModel.postsToApproveFlow.value)
                        true
                    }
                    R.id.flag_post_item -> {
                        true
                    }
                    R.id.flag_post_item_important ->
                    {
                        viewModel.flagPost(position,Flag.IMPORTANT)
                        approvePostsAdapter.submitList(viewModel.postsToApproveFlow.value)
                        true

                    }
                    R.id.flag_post_item_trivial -> {
                        viewModel.flagPost(position,Flag.TRIVIAL)
                        approvePostsAdapter.submitList(viewModel.postsToApproveFlow.value)
                        true
                    }
                    R.id.flag_post_item_other -> {
                        viewModel.flagPost(position,Flag.NONE)
                        approvePostsAdapter.submitList(viewModel.postsToApproveFlow.value)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            inflate(R.menu.approve_posts_menu)
            show()
        }
    }

    override fun onItemClick(position: Int, v: View, v2:View?) {
        if(v2 != null)
            showMenu(v2,position)
    }

    override fun onItemLongClick(position: Int, v: View) {
    }
}