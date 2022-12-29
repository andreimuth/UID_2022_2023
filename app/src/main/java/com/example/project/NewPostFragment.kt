package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {

    private lateinit var binding: FragmentNewPostBinding
    private val viewModel: SharedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        initClickListeners()
        return binding.root
    }

    private fun initClickListeners() {
        binding.postButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.closeButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}