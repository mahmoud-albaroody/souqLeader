package com.alef.souqleader.data.remote.dto

data class ChannelReport(
    val channel_title: String,
    val fresh_leads: Int,
    val percentage_leads: String,
    val total: Int,
    val total_leads: Int
)