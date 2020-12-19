package com.example.listingapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.listingapp.model.ApixuWeatherResponse
import com.example.listingapp.model.CountryDetails
import com.example.listingapp.model.RetrofitAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.listingapp.Util
import com.example.listingapp.roomdata.AppDatabase

class ListViewModel(application: Application): AndroidViewModel(application) {
    private val TAG ="ListViewModel"
    val countryList:LiveData<List<CountryDetails>> = MutableLiveData<List<CountryDetails>>()
    val weatherReport:LiveData<ApixuWeatherResponse> = MutableLiveData<ApixuWeatherResponse>()
    private val apiService=RetrofitAPI()
    init {
        viewModelScope.launch {
            weatherReport as MutableLiveData
            countryList as MutableLiveData
            Util.BASE_URL = "http://api.weatherstack.com/"
            //weatherReport.value = getWeatherData()
            Util.BASE_URL = "https://restcountries.eu/rest/"
            //countryList.value =  countryDetailsAPI()
//            loadToDatabase(countryList)
        }
    }

    private suspend fun getWeatherData() : ApixuWeatherResponse? {
      return withContext(Dispatchers.IO){
          RetrofitAPI().getWeatherReport("CHENNAI").body()
      }
    }
    private suspend fun loadToDatabase(countryList: List<CountryDetails>) {
        val dummyResponseList = ArrayList<CountryDetails>()

        AppDatabase.invoke(getApplication()).countryDao().insertAll(*countryList.toTypedArray())
        //dummyResponse.value  = AppDatabase.invoke(getApplication()).countryDao().getAll()
    }
    private suspend fun countryDetailsAPI ():List<CountryDetails>?{
      return withContext(Dispatchers.IO){
            RetrofitAPI().getDetails().body()
      }
    }

}