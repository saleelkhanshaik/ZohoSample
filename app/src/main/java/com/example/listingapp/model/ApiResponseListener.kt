package com.example.listingapp.model

interface ApiResponseListener {
    fun onSuccess(apiResponse: List<*>){

    }
    fun onFailure(onFailure: String)
    fun onError(onError: String)
    fun onSuccess(apiResponse: ApixuWeatherResponse?) {

    }
}