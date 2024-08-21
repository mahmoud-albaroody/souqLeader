package com.alef.souqleader.data.remote.dto

data class CancelationReport(
    val leads: List<Lead>,
    val reasons_chart: List<Chart>,
    val total_actions: String,
    val total_activity: String,
    val total_canceled: String
)