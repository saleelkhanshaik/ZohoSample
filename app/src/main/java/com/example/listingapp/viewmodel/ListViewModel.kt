package com.example.listingapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.listingapp.model.ApixuWeatherResponse
import com.example.listingapp.model.CountryDetails
import com.example.listingapp.model.RetrofitAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.listingapp.Util
import com.example.listingapp.roomdata.AppDatabase

class ListViewModel:BaseViewModel() {
    private val TAG ="ListViewModel"
    val countryList:LiveData<List<CountryDetails>> = MutableLiveData<List<CountryDetails>>()
    val weatherReport:LiveData<ApixuWeatherResponse> = MutableLiveData<ApixuWeatherResponse>()

    init {
        viewModelScope.launch {
            weatherReport as MutableLiveData
            countryList as MutableLiveData
//            Util.BASE_URL = "http://api.weatherstack.com/"
            //weatherReport.value = getWeatherData()
            Util.BASE_URL = "https://restcountries.eu/"
//            countryList.value =  countryDetailsAPI()
//            loadToDatabase(countryDetailsAPI()!!)
//            countryList.value = loadFromDatabase()
//            countryList.value = loadFromDatabaseQuery("ind")
        }
    }

     suspend fun getWeatherData() : ApixuWeatherResponse? {
      return withContext(Dispatchers.IO){
          RetrofitAPI().getWeatherReport("CHENNAI").body()
      }
    }
     suspend fun loadToDatabase(countryList: List<CountryDetails>,context: Context) {
        AppDatabase.invoke(context.applicationContext).countryDao().insertAll(*countryList.toTypedArray())
    }
     suspend fun loadFromDatabase(context: Context):List<CountryDetails>? {
        return withContext(Dispatchers.IO){
            AppDatabase.invoke(context.applicationContext).countryDao().getAll()
        }
    }
     suspend fun loadFromDatabaseQuery( name:String,context: Context):List<CountryDetails>? {
        return withContext(Dispatchers.IO){
            AppDatabase.invoke(context.applicationContext).countryDao().loadByName(name)
        }
    }
     suspend fun countryDetailsAPI ():List<CountryDetails>?{
      return withContext(Dispatchers.IO){
            RetrofitAPI().getDetails().body()
      }
    }

}