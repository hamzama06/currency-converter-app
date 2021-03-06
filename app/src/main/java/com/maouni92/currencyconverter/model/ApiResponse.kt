package com.maouni92.currencyconverter.model

data class ApiResponse(
    val amount: String,
    val base_currency_code: String,
    val base_currency_name: String,
    val rates: HashMap<String, Rates> = HashMap(),
    val status: String,
    val updated_date: String
)