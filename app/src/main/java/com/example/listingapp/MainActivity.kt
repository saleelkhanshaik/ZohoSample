package com.example.listingapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.listingapp.model.ApixuWeatherResponse
import com.example.listingapp.model.CountryDetails
import com.example.listingapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showSplashScreenAnimation()
    }

    private fun showSplashScreenAnimation() {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.maincontainer,ListFragment(),"ListFragment")
        fragment.commit()
    }
    public fun updateweatherCard(it: ApixuWeatherResponse?) {
        it?.let {
            temperature.text = this.getString(R.string.temp, it.current?.temperature.toString())
            cityName.text = it.location?.name
            weatherdescriptions.text = it.current?.weatherDescriptions?.get(0) ?: ""
            weathericons.load(it.current?.weatherIcons?.get(0) ?: "")
        }
    }

    fun addChildFragment( countryListAdapter:CountryListAdapter,position:Int) {
        val countryInfo =countryListAdapter.coutryList[position]
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(
                R.id.maincontainer, DetailsCountry.newInstance(
                countryInfo.flag!!,
                countryInfo.name!!,
                countryInfo.capital!!,
                countryInfo.region!!.toString(),
                countryInfo.area!!.toString()
        ), "countryDetails"
        )
        fragmentTransaction.commit()
    }
}