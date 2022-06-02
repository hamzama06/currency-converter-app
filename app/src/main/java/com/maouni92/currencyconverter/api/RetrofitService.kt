package com.maouni92.currencyconverter.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitService {
    companion object{
        private var retrofit: Retrofit? = null

        val getClient : Retrofit get(){
            if (retrofit == null){
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Endpoints.BASE_URL)
                    .build()
            }
           return retrofit!!
        }
    }
}