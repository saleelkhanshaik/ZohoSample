package com.example.listingapp.model

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryAPI {
    @GET("rest/v2/all")
     fun getCountryDetails(): Call<List<CountryDetails>>
   // http://api.weatherstack.com/current?access_key=6b5d19e6796e9b6e36ce0015afe42849&%20query=Chennai
    @GET("current?access_key=6b5d19e6796e9b6e36ce0015afe42849")
     fun getWeatherData(
        @Query(value = "query") location:String
    ):Call<ApixuWeatherResponse>
}