package com.maouni92.currencyconverter.model

data class Rates(
    val currency_name: String,
    val rate: String,
    val rate_for_amount: String
)