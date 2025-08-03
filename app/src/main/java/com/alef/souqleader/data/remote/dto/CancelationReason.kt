package com.alef.souqleader.data.remote.dto

import android.os.Build

import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.getCreatedAt


data class CancelationReason(
    val created_at: String,
    val id: Int,
    val title_ar: String,
    val title_en: String,
    val updated_at: String
) {
    fun getTitle(): String {
        return if (AccountData.lang == "en") {
            title_en
        } else {
            title_ar
        }
    }


        fun getDate():String? {
            return created_at?.let { getCreatedAt(it) }
        }

}