package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class City(
    val id: Int,
    val title_ar: String,
    val title_en: String
){
    fun getCity():String{
        return if(AccountData.lang=="ar") {
            title_ar
        }else{
            title_en
        }
    }
}