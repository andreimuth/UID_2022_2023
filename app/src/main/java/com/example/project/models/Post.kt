package com.example.project.models

data class Post(
    val id: Int,
    val userIcon: Int,
    val username: String,
    val dateCreated: String,
    val text: String,
    val comments: MutableList<Comment>,
    val flag: Flag,
    val type: PostType
)