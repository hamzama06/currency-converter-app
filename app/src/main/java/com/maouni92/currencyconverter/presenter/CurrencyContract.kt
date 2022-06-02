package com.maouni92.currencyconverter.presenter

import com.maouni92.currencyconverter.model.ApiResponse

interface CurrencyContract {

    interface Repository{
        interface OnFinishedListener{
            fun onFinished(convertedData:ApiResponse)
            fun onFailure(t:Throwable)
        }
        fun getConvertedData(onFinishedListener: OnFinishedListener, from:String, to:String, amount:Double)
    }

    interface CurrencyView{
        fun showProgress()
        fun hideProgress()
        fun displayData(data:ApiResponse)
        fun onResponseFailure(t:Throwable)
    }

    interface Presenter{

        fun getConvertedCurrency(from:String, to:String, amount:Double)
        fun onDestroy()
    }
}