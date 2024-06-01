package com.alef.souqleader

import okhttp3.ResponseBody

// A generic class that contains Data and status about loading this Data.
sealed class Resource<T>(
    val data: T? = null, val errorCode: Int? = null,
    val errorBody: ResponseBody? = null
) {
    class Success<T>(data: T, errorBody: ResponseBody?) :
        Resource<T>(data, null, errorBody)

    class Loading<T>(data: T? = null) : Resource<T>(data)

    class DataError<T>(data: T? = null, errorCode: Int, errorBody: ResponseBody?) :
        Resource<T>(null, errorCode, errorBody)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[offerData=$data]"
            is DataError<T> -> "Error[exception=$errorCode]"
            is Loading<T> -> "Loading"
        }
    }

}


