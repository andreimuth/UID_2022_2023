package com.example.project.models

data class Group(
    val id: Int,
    val groupName: String,
    val users: MutableList<User>
)
