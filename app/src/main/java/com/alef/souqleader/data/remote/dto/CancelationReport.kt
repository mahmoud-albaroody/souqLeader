package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.data.remote.Info

data class CancelationReport(
    val leads: List<Lead>,
    val reasons_chart: List<Chart>,
    val total_actions: String,
    val total_activity: String,
    val total_canceled: String,
    val pagination:Info
)