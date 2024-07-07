package com.alef.souqleader.domain

import com.alef.souqleader.data.di.networkModule.BaseUrlInterceptor
import javax.inject.Inject

class NetworkManager @Inject constructor(private val baseUrlInterceptor: BaseUrlInterceptor) {

    fun changeBaseUrl(newUrl: String) {
        baseUrlInterceptor.setNewUrl(newUrl)
    }
}