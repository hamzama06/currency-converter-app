package com.maouni92.currencyconverter.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.snackbar.Snackbar
import com.maouni92.currencyconverter.R
import com.maouni92.currencyconverter.databinding.ActivityMainBinding
import com.maouni92.currencyconverter.helper.Utility
import com.maouni92.currencyconverter.model.ApiResponse
import com.maouni92.currencyconverter.presenter.CurrencyContract
import com.maouni92.currencyconverter.presenter.CurrencyPresenter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), CurrencyContract.CurrencyView, TextWatcher {


    private lateinit var binding: ActivityMainBinding
    private var presenter: CurrencyPresenter? = null
    private var selectedFromCurrencyItem: String? = null
    private var selectedToCurrencyItem: String? = null
    private var currenciesList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)

        initUi()
        presenter = CurrencyPresenter(this)

        binding.amountField.addTextChangedListener(this)

        binding.convertButton.setOnClickListener {
            makeConversion()
        }
        binding.exchangeButton.setOnClickListener { switchCurrencies() }


    }

    // Initialize spinners and textViews
    private fun initUi() {

        val fromSpinner = binding.currencyFromSpinner
        val toSpinner = binding.currencyToSpinner

        currenciesList = getAllCurrencies()
        currenciesList!!.sort()

        selectedFromCurrencyItem = currenciesList!![0]
        selectedToCurrencyItem = currenciesList!![1]

        val adapter = ArrayAdapter(this, R.layout.spinner_item, currenciesList!!)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter
        fromSpinner.setSelection(0)
        toSpinner.setSelection(1)



        fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                selectedFromCurrencyItem = currenciesList!![position]

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }



        toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedToCurrencyItem = currenciesList!![position]

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }


    }

    private fun makeConversion() {

        if (Utility().isNetworkAvailable(this)) {
            val amount = binding.amountField.text.toString()
            presenter!!.getConvertedCurrency(
                getCurrencyCode(selectedFromCurrencyItem!!),
                getCurrencyCode(selectedToCurrencyItem!!),
                amount.toDouble()
            )
        } else {
            Snackbar.make(
                binding.root,
                getString(R.string.network_connection_message),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun getAllCurrencies(): ArrayList<String> {
        val currencies = ArrayList<String>()
        val locals = Locale.getAvailableLocales()
        for (local in locals) {
            try {
                val currency: Currency = Currency.getInstance(local)
                val currencyItem = "${currency.currencyCode} - ${currency.displayName}"

                if (!currencies.contains(currencyItem)) {
                    currencies.add(currencyItem)

                }

            } catch (e: Exception) {

            }
        }
        return currencies

    }

    // Get currency code from full currency name
    private fun getCurrencyCode(currencyName: String): String {
        return currencyName.split('-')[0].trim()
    }

    // Switch between currencies spinners
    private fun switchCurrencies() {

        val indexFrom = binding.currencyFromSpinner.selectedItemPosition

        val indexTo = binding.currencyToSpinner.selectedItemPosition


        binding.currencyFromSpinner.setSelection(indexTo)
        binding.currencyToSpinner.setSelection(indexFrom)

        selectedToCurrencyItem = currenciesList!![indexFrom]
        selectedFromCurrencyItem = currenciesList!![indexTo]

    }


    private fun enableProgress(enabled: Boolean) {
        binding.convertButton.isEnabled = !enabled
        binding.convertButton.text = if (enabled) "" else "Convert"
        binding.progressBar.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun showProgress() {
        enableProgress(true)
    }

    override fun hideProgress() {
        enableProgress(false)
    }

    override fun displayData(data: ApiResponse) {

        val currencyCodeFrom = getCurrencyCode(selectedFromCurrencyItem!!)
        val currencyCodeTo = getCurrencyCode(selectedToCurrencyItem!!)
        binding.convertFromTv.text = data.amount
        binding.currencyFromTv.text = data.base_currency_name
        binding.currencyToTv.text = data.rates[currencyCodeTo]!!.currency_name
        binding.convertToTv.text = data.rates[currencyCodeTo]!!.rate_for_amount
        binding.updatedDateTv.visibility = View.VISIBLE
        binding.updatedDateTv.text = "Updated date: ${data.updated_date}"
        binding.unitAmountTv.visibility = View.VISIBLE
        binding.unitAmountTv.text =
            "1 $currencyCodeFrom =  ${data.rates[currencyCodeTo]!!.rate} $currencyCodeTo"

    }

    override fun onResponseFailure(t: Throwable) {
        hideProgress()
    }


    ///////////////////*  OnChange text listener  *////////////////////////
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text == null) {
            binding.convertButton.isEnabled = false
            return
        }
        binding.convertButton.isEnabled = text.trim().isNotEmpty() && text.trim() != "0"
    }

    override fun afterTextChanged(ediText: Editable?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onDestroy()
    }
}