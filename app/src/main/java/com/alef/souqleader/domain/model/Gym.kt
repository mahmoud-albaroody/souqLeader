package com.alef.souqleader.domain.model

import java.io.Serializable

data class Gym(
    val gymId: Int,
    val gymTitle: String,
    val gymIcon: Int? = null,
    val gymDescription: String,
    var rate: Double
) : Serializable

val listOfGym = listOf(
    Gym(1, "title", 0, "Description", 0.5),
    Gym(2, "title", 0, "Description", 0.5),
)