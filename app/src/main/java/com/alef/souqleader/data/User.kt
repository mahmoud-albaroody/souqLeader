package com.alef.souqleader.data

data class User(
    val email: String,
    val id: Int,
    val name: String,
    val photo: String,
    val role: Role
)