package com.example.listingapp.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
    lateinit var apiInterface: CountryAPI
    private fun setRetrofit(url: String) {
        val httpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(url).build()
        apiInterface = retrofit.create(CountryAPI::class.java)
    }

    fun getApi(url: String): CountryAPI {
        setRetrofit(url)
        return apiInterface
    }

}