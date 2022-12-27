package com.example.project.models

data class Comment(
    val id: Int,
    val userIcon: Int,
    val username: String,
    val dateCreated: String,
    val commentText: String
)