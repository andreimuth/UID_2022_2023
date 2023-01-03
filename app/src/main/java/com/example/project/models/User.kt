package com.example.project.models

data class User(
    val id: Int,
    val userIcon: Int,
    val username: String,
    val password: String,
    val type: UserType,
)
