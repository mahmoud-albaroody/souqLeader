package com.alef.souqleader.data.remote.dto

data class JopPersion(
    val area: Area,
    val career_level: CareerLevel,
    val category: List<Category>,
    val city: City,
    val country: Country,
    val description: String,
    val id: Int,
    val title: String,
    val type: List<Type>,
    val unlocked_count: Int,
    val workplace: String
)