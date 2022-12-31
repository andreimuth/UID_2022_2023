package com.example.project

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.FragmentPostDetailsBinding
import com.example.project.models.Comment
import com.example.project.models.Flag

class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailsBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val args: PostDetailsFragmentArgs by navArgs()

    private lateinit var myAdapter: CommentFeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        initClickListeners()
        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val post = viewModel.feedPosts[args.postId.toInt()]
        binding.postText.text = post.text
        binding.date.text = post.dateCreated
        binding.username.text = post.username

        if(post.flag != Flag.NONE) {
            if(post.flag == Flag.IMPORTANT)
                binding.flagIcon.setImageResource(R.drawable.ic_important)
            else
                binding.flagIcon.setImageResource(R.drawable.ic_trivial)
        }

        binding.postCommentButton.setOnClickListener{
            val comment = binding.commentInputText.text.toString()
            viewModel.addComment(
                post.id,
                Comment(post.comments.size + 1, 0, "username", "date", comment)
            )
            binding.commentInputText.text.clear()
            myAdapter.notifyItemInserted(post.comments.size + 1)
        }

        binding.likeButton.colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)

        binding.likeButton.setOnClickListener {
            val color = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            val colorClicked = PorterDuffColorFilter(Color.rgb(187, 134, 252), PorterDuff.Mode.SRC_ATOP)
            if(binding.likeButton.colorFilter == color)
                binding.likeButton.colorFilter = colorClicked
            else
                binding.likeButton.colorFilter = color
        }
    }

    private fun initRecyclerView() {
        myAdapter = CommentFeedAdapter(viewModel.feedPosts[args.postId.toInt()].comments)
        binding.commentsRecyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.reportButton.setOnClickListener {
            Toast.makeText(context, "Post reported", Toast.LENGTH_SHORT).show()
        }
    }

}