package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class Activityy(
    val activity_by: ActivityBy,
    val created_at: String,
    val description: String,
    val description_ar: String,
    val id: Int,
    val link: String,
    val page_id: Int,
    val page_name: String
){
    fun getDescriptions(): String {
        return if(AccountData.lang =="en") {
            description
        }else{
            description_ar
        }
    }
}