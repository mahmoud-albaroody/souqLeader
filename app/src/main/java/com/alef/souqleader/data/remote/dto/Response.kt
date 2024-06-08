package com.alef.souqleader.data.remote.dto

import com.google.gson.annotations.SerializedName

open class StatusResponse(
    @SerializedName("isSuccessed")
    val isSuccessed: Boolean = false,

    @SerializedName("error")
    val error: String? = ""
)

data class LeadsByStatusResponse(
    val status: Boolean = false,
    var data: ArrayList<Lead>? = null
) : StatusResponse()

data class LeadsStatusResponse(
    val status: Boolean = false,
    var data: ArrayList<LeadStatus>? = null
) : StatusResponse()

data class ConvertResponse(
    val date: String,
    val info: Info?,
    val query: Query?,
    val result: Double,
    val success: Boolean
) : StatusResponse()

data class TimeSeriesResponse(
    val base: String? = null,
    val end_date: String? = null,
    val rates: Any? = null,
    val start_date: String? = null,
    val success: Boolean,
    val timeseries: Boolean
) : StatusResponse()

data class CurrencyResponse(
    var success: Boolean? = null,
    var timestamp: Long? = null,
    var historical: Boolean? = null,
    var base: String? = null,
    var date: String? = null,
    var rates: Any? = null
) : StatusResponse()
