package com.alef.souqleader.data.remote.dto

import androidx.compose.runtime.MutableState

data class Dashboard(
    val activeInventory: Int,
    val active_delayed_leads: Int,
    val active_lead_chart: ActiveLeadChart,
    val conversion_rate: Double,
    val fresh_leads: String,
    val inventoryChart: List<Chart>,
    val lead_source: List<LeadSource>,
    val online_users: String,
    val reasons_chart: List<Chart>,
    val stage_delay: StageDelay,
    val top_agents: List<TopAgent>,
    val totalHotActiveLead: String,
    val total_active_lead: String
)