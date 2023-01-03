package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project.databinding.FragmentGroupsBinding
import com.example.project.databinding.FragmentNewGroupBinding

class NewGroupFragment: Fragment() {

    private lateinit var binding: FragmentNewGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewGroupBinding.inflate(inflater, container, false)
        return binding.root;
    }

}