package com.alef.souqleader.data.remote.dto

data class LeadDetails(
    val actions: ArrayList<Action> = arrayListOf(),
    val lead: Lead
)