package com.maouni92.currencyconverter.presenter

import com.maouni92.currencyconverter.api.ApiService
import com.maouni92.currencyconverter.api.RetrofitService
import com.maouni92.currencyconverter.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyRepo : CurrencyContract.Repository{
    override fun getConvertedData(
        onFinishedListener: CurrencyContract.Repository.OnFinishedListener,
        from: String,
        to: String,
        amount: Double
    ) {
        val apiService = RetrofitService.getClient.create(ApiService::class.java)
        val call : Call<ApiResponse> = apiService.convert(from, to, amount)
        call.enqueue(object : Callback<ApiResponse?> {
            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
               if (response.isSuccessful){
                   val convertedData = response.body()
                   onFinishedListener.onFinished(convertedData!!)
               }
            }

            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })

    }
}