package com.alef.souqleader.data.remote.dto

data class MeetingReport(
    val channel_chart: List<ChannelChart>,
    val chart: List<Chart>,
    val leads: List<Lead>,
    val project_chart: List<ProjectChart>,
    val the_best: TheBest,
    val totalLeads: Int,
    val total_actions: Int,
    val total_activity: Int,
    val total_meeting: Int
)