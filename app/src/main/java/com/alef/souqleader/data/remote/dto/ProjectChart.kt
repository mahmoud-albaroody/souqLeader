package com.alef.souqleader.data.remote.dto

data class ProjectChart(
    val lead_count: String,
    val id: Int,
    val lead_percentage: Float,
    val leads: String?,
    val title: String
)