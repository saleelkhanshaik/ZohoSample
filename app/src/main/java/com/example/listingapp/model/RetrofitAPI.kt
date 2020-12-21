package com.example.listingapp.model

import com.example.listingapp.Util
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
//    private val BASE_URL="https://restcountries.eu/"
//    private val api = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .
//        .build()
//        .create(CountryAPI::class.java)
//     fun getDetails():Call<List<CountryDetails>>{
//        return api.getCountryDetails()
//    }
//     fun getWeatherReport(currentLocation:String):Callback<ApixuWeatherResponse>{
//        return api.getWeatherData(currentLocation)
//    }
    private fun setRetrofit(url: String) {
        val httpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(url).build()
        apiInterface = retrofit.create(CountryAPI::class.java)
    }



        lateinit var apiInterface: CountryAPI

        fun getApi(url: String): CountryAPI {
            setRetrofit(url)
            return apiInterface
        }

}