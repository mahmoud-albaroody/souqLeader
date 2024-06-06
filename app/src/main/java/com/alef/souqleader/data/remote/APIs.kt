package com.alef.souqleader.data.remote

import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import retrofit2.Response
import retrofit2.http.*


interface APIs {


    @GET("leadsByStatus")
    suspend fun leadsByStatus(): Response<LeadsByStatusResponse>

//    @GET("convert")
//    suspend fun convert(
//        @Header("apikey") token: String = ACCESS_TOKEN,
//        @Query("to") to: String,
//        @Query("from") from: String,
//        @Query("amount") amount: String
//    ): Response<ConvertResponse>
//

//    @GET("symbols")
//    suspend fun symbols(
//        @Header("apikey") token: String = ACCESS_TOKEN,
//    ): Response<SymbolsResponse>

//    @GET("{date}")
//    suspend fun getCurrencyData(
//        @Path("date") date: String,
//        @Query("apikey") access_key: String = ACCESS_TOKEN,
//        @Query("base") base: String
//    ): Response<CurrencyResponse>

}