package com.example.listingapp.model

import android.util.Log
import com.example.listingapp.AppState
import com.example.listingapp.roomdata.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeModel {
    fun callCountryAPI(listener: ApiResponseListener) {
        val data: Call<List<CountryDetails>> =
            RetrofitAPI.getApi("https://restcountries.eu/").getCountryDetails()
        data.enqueue(object : Callback<List<CountryDetails>> {
            override fun onFailure(call: Call<List<CountryDetails>>, t: Throwable) {
                val countryDetails = appDatabase.countryDao()
                var list:List<CountryDetails> = listOf()
                Thread() {
                   list=countryDetails.getAll()
                    if (list.isNotEmpty()) {
                        listener.onSuccess(list)
                    } else {
                        listener.onFailure("onFailure")
                    }
                }.start()
            }

            override fun onResponse(
                call: Call<List<CountryDetails>>,
                response: Response<List<CountryDetails>>
            ) {
                val countryDetails = appDatabase.countryDao()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        listener.onSuccess(response.body()!!)

                        val t: Thread = object : Thread() {
                            override fun run() {
                                countryDetails.insertAll(*response.body()!!.toTypedArray())
                            }
                        }
                        t.start()
                    }
                } else {
                    if(countryDetails.getAll().isNotEmpty()){
                        listener.onSuccess(countryDetails.getAll())
                    }else{
                        listener.onFailure("onFailure")
                    }
                }
            }
        })
    }

    fun callWeatherDetails(listener: ApiResponseListener, queryString: String) {
        val data: Call<ApixuWeatherResponse> =
            RetrofitAPI.getApi("http://api.weatherstack.com/").getWeatherData(queryString)
        data.enqueue(object : Callback<ApixuWeatherResponse> {
            override fun onFailure(call: Call<ApixuWeatherResponse>, t: Throwable) {
                listener.onFailure("Weather Failure")
            }

            override fun onResponse(
                call: Call<ApixuWeatherResponse>,
                response: Response<ApixuWeatherResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        listener.onSuccess(response.body())
                    }
                } else {
                    listener.onError("Weather Error--")
                }
            }

        })
    }
    private val appDatabase: AppDatabase = AppState.appDatabase

    fun searchCountryAPI(listener: ApiResponseListener,query:String) {
        Thread(){
            val list = appDatabase.countryDao().loadByName(query)
            Log.d("searchCountryAPI", "searchCountryAPI: "+list.size)
            if(list.isNotEmpty()){
                listener.onSuccess(list.distinct()
                )
            }else{
                listener.onFailure("No Data Found")
            }
        }.start()
    }

}