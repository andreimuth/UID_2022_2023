package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.FragmentLoginBinding
import com.example.project.models.User

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initClickListeners()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initClickListeners() {
        binding.loginButton.setOnClickListener {
            binding.loginError.text = ""
            val user: User? = viewModel.users.find{ user -> user.username == binding.loginUsernameTextInput.text.toString() &&
                                    user.password == binding.loginPasswordTextInput.text.toString()}

            if(user != null) {
                if(!user.isBanned) {
                    viewModel.loggedInUser = user
                    findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
                } else {
                    binding.loginError.text = "This user has been banned!"
                }
            } else {
                binding.loginError.text = "The username or password is incorrect!"
            }
        }
        binding.registerTextView.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginToRegister())
        }
    }
}