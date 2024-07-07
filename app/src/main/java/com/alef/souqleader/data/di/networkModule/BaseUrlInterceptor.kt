package com.alef.souqleader.data.di.networkModule

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseUrlInterceptor @Inject constructor() : Interceptor {

    @Volatile
    private var newUrl: HttpUrl? = null

    fun setNewUrl(url: String) {
        this.newUrl = url.toHttpUrlOrNull()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        newUrl?.let {
            val newUrl = it.newBuilder()
                .addPathSegments(request.url.encodedPath.removePrefix("/"))
                .encodedQuery(request.url.encodedQuery)
                .build()
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(request)
    }
}