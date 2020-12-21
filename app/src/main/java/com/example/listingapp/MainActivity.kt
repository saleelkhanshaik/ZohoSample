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
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.listingapp.model.ApixuWeatherResponse
import com.example.listingapp.model.CountryDetails
import com.example.listingapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CountryListAdapter.ItemClick {
    private lateinit var job: Job
    private lateinit var listViewModel: ListViewModel
    private val TAG = "MainActivity"
    private val PERMISSION_SEND_SMS = 320
    private lateinit var countryListAdapter: CountryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkSmsPermission()
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            countryListAdapter = CountryListAdapter(this@MainActivity,  this@MainActivity)
            adapter = countryListAdapter
        }

        allCountryDetails()
        listViewModel.getWeatherDetails("CHENNAI").observe(this, Observer {
            updateweatherCard(it)
        })
        symbol_searchView.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(it.length>2){
                        listViewModel.searchDetails(it.toString()).observe(this@MainActivity,
                            Observer {country ->
                                Log.d(TAG, "onTextChanged: "+country.size)
                            if(country != null ) {

                                updateAdapter(country)
                            }
                        })
                    }else{
                        allCountryDetails()
                    }
                }


            }
        })

    }

    private fun updateweatherCard(it: ApixuWeatherResponse?) {
        it?.let {
            temperature.text = this.getString(R.string.temp, it.current?.temperature.toString())
            cityName.text = it.location?.name
            weatherdescriptions.text = it.current?.weatherDescriptions?.get(0) ?: ""
            weathericons.load(it.current?.weatherIcons?.get(0) ?: "")
        }
    }

    private fun checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                //todo - string files
                AlertDialog.Builder(this).setTitle("External storage permission")
                    .setMessage("This app requires access to External storage")
                    .setPositiveButton("Ask me") { dialog, which ->
                        requestPermission()
                    }
                    .setNegativeButton("Not now") { dialog, which ->
                        notifyUser(false)
                    }.show()
            } else {
                requestPermission()
            }
        } else {
            notifyUser(true)
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_SEND_SMS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_SEND_SMS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    notifyUser(true)
                } else {
                    notifyUser(false)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun notifyUser(permissionGranted: Boolean) {
        if (permissionGranted) {
            //API call
        } else {
            Toast.makeText(this, "This app requires access to External storage", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
    }

    private fun updateAdapter(it: List<CountryDetails>) {
        if (it.isNotEmpty()) {
            progressBar.visibility = View.GONE
            nodataa.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            countryListAdapter.run {
                coutryList.clear()
                coutryList.addAll(it)
                notifyDataSetChanged()
            }
        } else {
            recyclerView.visibility = View.GONE
            progressBar.visibility = View.GONE
            nodataa.visibility = View.VISIBLE
        }

    }

    override fun onItemClick(position: Int) {
        val countryInfo =countryListAdapter.coutryList[position]
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.add(
            R.id.container, BlankFragment.newInstance(
                countryInfo.flag!!,
                countryInfo.name!!,
                countryInfo.capital!!,
                countryInfo.population!!.toString(),
                countryInfo.numericCode!!
            ), "countryDetails"
        )
        fragmentTransaction.commit()
    }
    fun allCountryDetails(){
        listViewModel.getCountryDetails().observe(this, Observer {
            Log.d(TAG, "countryList: " + it.size)
            updateAdapter(it)
            //data= it as ArrayList<CountryDetails>;
        })
    }
}