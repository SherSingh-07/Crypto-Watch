package com.example.cryptowatch.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val url = "https://api.coinmarketcap.com/data-api/v3/cryptocurrency/listing?start=1&limit=500"

object ApiUtilities {

    fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.coinmarketcap.com/")
//            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}