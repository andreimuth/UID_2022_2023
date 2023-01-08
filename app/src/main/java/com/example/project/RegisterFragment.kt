package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.FragmentRegisterBinding
import com.example.project.models.User
import com.example.project.models.UserType

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val types = resources.getStringArray(R.array.Types)

        val spinner = binding.root.findViewById<Spinner>(R.id.register_type_user_spinner_input)
        if (spinner != null) {
            val adapter = this.activity?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item, types
                )
            }
            spinner.adapter = adapter
        }
        initClickListeners()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initClickListeners() {
        binding.registerButton.setOnClickListener {

            binding.registerError.text = ""

            val user: User? = viewModel.users.find{ user -> user.username == binding.registerUsernameTextInput.text.toString()}

            if(user != null) {
                binding.registerError.text = "An user with given username already exists!"
            } else {
                if(binding.registerUsernameTextInput.text.toString().length < 3) {
                    binding.registerError.text = "Username too short!"
                } else {
                    if(binding.registerPasswordTextInput.text.toString().length < 3) {
                        binding.registerError.text = "Password too short!"
                    } else {
                        val userType: UserType =
                            when (binding.registerTypeUserSpinnerInput.selectedItem.toString()) {
                                "Moderator" -> UserType.MODERATOR
                                "Admin" -> UserType.ADMIN
                                "Academic" -> UserType.ACADEMIC
                                else -> UserType.STUDENT
                            }
                        viewModel.addUser(
                            User(
                                viewModel.users.size + 1,
                                1,
                                binding.registerUsernameTextInput.text.toString(),
                                binding.registerPasswordTextInput.text.toString(),
                                userType,
                                false
                            )
                        )
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin())
                    }
                }
            }
        }
    }
}