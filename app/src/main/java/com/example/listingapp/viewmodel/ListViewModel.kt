package com.example.listingapp.viewmodel

import androidx.lifecycle.*
import com.example.listingapp.model.*

class ListViewModel:ViewModel() {
    private val TAG ="ListViewModel"

    val weatherReport:LiveData<ApixuWeatherResponse> = MutableLiveData<ApixuWeatherResponse>()
    private lateinit var allCountryList: MutableLiveData<List<CountryDetails>>
    private lateinit var countryList: MutableLiveData<List<CountryDetails>>
    private lateinit var weatherDetails :MutableLiveData<ApixuWeatherResponse>
    //country API
    //weather API
    //room-db store
    private var homeModel: HomeModel = HomeModel()
    fun getCountryDetails():MutableLiveData<List<CountryDetails>>{
        allCountryList = MutableLiveData()
        homeModel.callCountryAPI(object : ApiResponseListener{
            override fun onSuccess(apiResponse: List<*>) {
                allCountryList.postValue(apiResponse as List<CountryDetails>)
            }

            override fun onFailure(onFailure: String) {
            }

            override fun onError(onError: String) {
            }
        })
        return allCountryList
    }
    fun getWeatherDetails(queryString:String):MutableLiveData<ApixuWeatherResponse>{
        weatherDetails  = MutableLiveData()
        homeModel.callWeatherDetails(object :ApiResponseListener{
            override fun onFailure(onFailure: String) {

            }

            override fun onError(onError: String) {

            }

            override fun onSuccess(apiResponse: ApixuWeatherResponse?) {
                weatherDetails.value = apiResponse
            }
        },queryString)
        return weatherDetails
    }

    fun searchDetails(queryString:String):MutableLiveData<List<CountryDetails>>{
        countryList = MutableLiveData()
//        val list = allCountryList.value?.toMutableList()
//        countryList.postValue(list?.filter { it.name!!.contains(queryString.trim(),ignoreCase = true) })
        homeModel.searchCountryAPI(object :ApiResponseListener{
            override fun onFailure(onFailure: String) {

            }

            override fun onError(onError: String) {

            }

            override fun onSuccess(apiResponse: List<*>) {
                countryList.value
                countryList.postValue(apiResponse as List<CountryDetails>)
            }
        },queryString)
        return countryList
    }
}