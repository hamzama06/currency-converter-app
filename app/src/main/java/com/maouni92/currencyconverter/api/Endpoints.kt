package com.maouni92.currencyconverter.api

interface Endpoints {
    companion object {

        const val BASE_URL = "https://api.getgeoapi.com/v2/currency/"

        // Api key
        const val API_KEY = "d929631223c4b93e502acb96f5a015ccc8d11aab"

        // Convert url path
        const val CONVERT_URL = "${BASE_URL}convert?api_key=${API_KEY}"
    }
}