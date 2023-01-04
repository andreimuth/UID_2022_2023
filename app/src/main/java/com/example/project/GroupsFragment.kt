package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentGroupsBinding
import com.example.project.models.Group
import com.example.project.models.UserType
import kotlinx.coroutines.launch

class GroupsFragment: Fragment(), OnItemClick {

    private lateinit var binding: FragmentGroupsBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val groupsAdapter: GroupsAdapter by lazy {
        GroupsAdapter(
            viewModel.groupsFlow.value,
            this,
            viewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        handleUserRole()
        initClickListeners()
        initRecyclerView()
        return binding.root;
    }

    override fun onItemClick(position: Int, v: View, v2: View?) {
        val group = viewModel.groupsFlow.value[position]
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Join ${group.groupName}?")
        builder.setMessage(group.groupDescription)
        builder.setPositiveButton("Join") { dialog, which ->
            joinGroup(group)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onItemLongClick(position: Int, v: View) {
        TODO("Not yet implemented")
    }

    private fun joinGroup(group: Group) {
        lifecycleScope.launch {
            viewModel.addUserToGroup(group)
        }
        groupsAdapter.notifyDataSetChanged()
    }

    private fun initClickListeners() {
        binding.addGroupButton.setOnClickListener {
            findNavController().navigate(GroupsFragmentDirections.actionGroupsToNewGroup())
        }

        lifecycleScope.launch {
            viewModel.groupsFlow.collect { groups ->
                groupsAdapter.refreshData(groups)
            }
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    viewModel.filterGroupsByKeyword(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println(newText)
                if(newText != null) {
                    viewModel.filterGroupsByKeyword(newText)
                }
                return false
            }
        })
    }

    private fun handleUserRole() {
        if(viewModel.loggedInUser.type == UserType.MODERATOR) {
            binding.addGroupButton.visibility = View.VISIBLE
        } else {
            binding.addGroupButton.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        groupsAdapter.refreshData(viewModel.groupsFlow.value)
        binding.groupsRecyclerView.apply {
            adapter = groupsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

}