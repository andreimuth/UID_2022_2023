package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentGroupsBinding

class GroupsFragment: Fragment(), OnItemClick {

    private lateinit var binding: FragmentGroupsBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        initClickListener()
        initRecyclerView()
        return binding.root;
    }


    override fun onItemClick(position: Int, v: View, v2: View?) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int, v: View) {
        TODO("Not yet implemented")
    }

    private fun initClickListener() {
        binding.addGroupButton.setOnClickListener {
            findNavController().navigate(GroupsFragmentDirections.actionGroupsToNewGroup())
        }
    }

    private fun initRecyclerView() {
        binding.groupsRecyclerView.apply {
            adapter = GroupsAdapter(viewModel.fillGroups, this@GroupsFragment)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

}