package com.example.project.models

data class Chat(
    val id: Int,
    val userIcon: Int,
    val usernameTo: String,
    val usernameFrom: String,
    val text: String,
    val dateSend: String,
    val status: ChatStatus
)
