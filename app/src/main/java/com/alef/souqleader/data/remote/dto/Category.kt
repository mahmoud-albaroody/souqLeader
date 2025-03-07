package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class Category(
    val created_at: String,
    val id: Int,
    val isActive: Int,
    val title: String,
    val title_ar: String,
    val title_en: String,
    val updated_at: Any
){
    fun getCategory():String{
        return if(AccountData.lang=="ar") {
            title_ar
        }else{
            title_en
        }
    }
}