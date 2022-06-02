package com.maouni92.currencyconverter.presenter

import com.maouni92.currencyconverter.model.ApiResponse

class CurrencyPresenter(currencyView: CurrencyContract.CurrencyView?) : CurrencyContract.Presenter,
    CurrencyContract.Repository.OnFinishedListener {

    private var view: CurrencyContract.CurrencyView? = currencyView
    private var currencyRepo = CurrencyRepo()

    override fun onFinished(convertedData: ApiResponse) {
        view!!.displayData(convertedData)

        if (view != null) {
            view!!.hideProgress()
        }

    }

    override fun onFailure(t: Throwable) {

        view!!.onResponseFailure(t)

        if (view != null) {
            view!!.hideProgress()
        }


    }

    override fun getConvertedCurrency(from: String, to: String, amount: Double) {

        currencyRepo.getConvertedData(this, from, to, amount)
        if (view != null) {
            view!!.showProgress()
        }

    }

    override fun onDestroy() {
        view = null
    }

}