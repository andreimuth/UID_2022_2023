package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.FragmentNewGroupBinding
import com.example.project.models.Group

class NewGroupFragment: Fragment() {

    private lateinit var binding: FragmentNewGroupBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewGroupBinding.inflate(inflater, container, false)
        initClickListeners()
        return binding.root;
    }

    private fun initClickListeners() {
        binding.closeButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.createGroupButton.setOnClickListener {
            createGroup()
        }
    }

    private fun createGroup() {
        if(binding.groupTitle.text.toString().isEmpty()) {
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage("Please enter a group name")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            return
        }
        Group(
            viewModel.groupsFlow.value.size,
            binding.groupTitle.text.toString(),
            binding.groupDescription.text.toString(),
            mutableListOf(),
            mutableListOf()
        ).also {
            viewModel.addGroup(it)
            findNavController().navigateUp()
        }

    }

}