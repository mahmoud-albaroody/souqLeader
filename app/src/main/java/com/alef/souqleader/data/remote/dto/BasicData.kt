package com.alef.souqleader.data.remote.dto

data class BasicData(
    val categories: ArrayList<Category>,
    val countries: ArrayList<Country>,
    val types: ArrayList<Type>,
    val careerLevels: ArrayList<CareerLevel>,

    )
data class Message(
    val id: String,
    val message: String,
    val subject: String,

    )
