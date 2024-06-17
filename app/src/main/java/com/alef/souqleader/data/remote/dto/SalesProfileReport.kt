package com.alef.souqleader.data.remote.dto

data class SalesProfileReport(
    val action_chart: List<ActionChart>,
    val answer: String,
    val arrange_meeting: String,
    val avg_response: String,
    val cancelReasons: List<CancelReason>,
    val created_today_lead: String,
    val done_meeting: String,
    val no_answer: String,
    val pending_cold: String,
    val pending_fresh: String,
    val status_counters: List<StatusCounter>,
    val total_calls: String,
    val user: User
)