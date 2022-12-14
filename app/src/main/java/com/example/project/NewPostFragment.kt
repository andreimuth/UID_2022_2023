package com.example.project

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.project.databinding.FragmentNewPostBinding
import com.example.project.models.Flag
import com.example.project.models.Post
import com.example.project.models.PostType
import com.example.project.models.UserType
import java.util.*

class NewPostFragment : Fragment() {

    private lateinit var binding: FragmentNewPostBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val args: NewPostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        handleUserRole()
        initClickListeners()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun handleUserRole() {
        if(viewModel.loggedInUser.type != UserType.ACADEMIC) {
            binding.titleToggleButton.text = "New Post"
            binding.titleToggleButton.icon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_empty) }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun initClickListeners() {
        if(viewModel.loggedInUser.type == UserType.ACADEMIC) {
            binding.titleToggleButton.setOnClickListener {
                if (binding.titleToggleButton.text == "New Post") {
                    binding.titleToggleButton.text = "New Announcement"
                } else {
                    binding.titleToggleButton.text = "New Post"
                }
            }
        }


        binding.postButton.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentDateAndTime = sdf.format(Date())
            viewModel.addPost(
                Post(
                    if(args.groupId == "-1") viewModel.feedPosts.size else viewModel.groups[args.groupId.toInt()].posts.size,
                    0,
                    viewModel.loggedInUser.username,
                    currentDateAndTime,
                    binding.postTextInput.text.toString(),
                    mutableListOf(),
                    Flag.NONE,
                    if(binding.titleToggleButton.text == "New Post") PostType.POST else PostType.ANNOUNCEMENT,
                    args.groupId
                )
            ).also {   // TODO: CREATE POST BASED ON TYPE
                findNavController().navigateUp()
            }
        }
        binding.closeButton.setOnClickListener {
            if(binding.postTextInput.text.isNotEmpty()) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Discard changes?")
                builder.setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    findNavController().navigateUp()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
            } else {
                findNavController().navigateUp()
            }

        }
    }
}