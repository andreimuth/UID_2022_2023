package com.example.project.models

data class Group(
    val id: Int,
    val groupName: String,
    val groupDescription: String,
    val users: MutableList<User>,
    val posts: MutableList<Post>
)
