package com.example.listingapp.model

import com.example.listingapp.Util
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAPI {
    private val BASE_URL="https://restcountries.eu/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountryAPI::class.java)
    suspend fun getDetails():Response<List<CountryDetails>>{
        return api.getCountryDetails()
    }
    suspend fun getWeatherReport(currentLocation:String):Response<ApixuWeatherResponse>{
        return api.getWeatherData(currentLocation)
    }
}