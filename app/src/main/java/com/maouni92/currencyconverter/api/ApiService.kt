package com.maouni92.currencyconverter.api

import com.maouni92.currencyconverter.model.ApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Endpoints.CONVERT_URL)
     fun convert(
        @Query("from") from:String,
        @Query("to") to:String,
        @Query("amount") amount:Double
    ): Call<ApiResponse>
}