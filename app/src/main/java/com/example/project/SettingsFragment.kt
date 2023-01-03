package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.FragmentSettingsBinding
import com.example.project.models.User
import com.example.project.models.UserType

class SettingsFragment: Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.usernameTextView.text = viewModel.loggedInUser.username
        binding.userTypeTextView.text = viewModel.loggedInUser.type.toString()
        initClickListeners()
        return binding.root;
    }

    private fun initClickListeners() {
        binding.logoutButton.setOnClickListener {
            viewModel.loggedInUser = User(-1, -1, "a", "a", UserType.STUDENT)
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToLogin())
        }
    }

}