package com.example.listingapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),CoroutineScope {
    private lateinit var job :Job
    private lateinit var listViewModel:ListViewModel
    private val TAG ="MainActivity"
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main
    private val PERMISSION_SEND_SMS = 320
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkSmsPermission()
        job = Job()
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.countryList.observe(this, Observer {
            Log.d(TAG, "countryList: "+it.size)
            updateAdapter(it)
        })
        listViewModel.weatherReport.observe(this, Observer {
                updateweatherCard(it)
        })
    }

    private fun updateweatherCard(it: ApixuWeatherResponse?) {
           it?.let {
               temperature.text = this.getString(R.string.temp,it.current?.temperature.toString())
               cityName.text = it.location?.name
               weatherdescriptions.text = it.current?.weatherDescriptions?.get(0) ?: ""
               weathericons.load(it.current?.weatherIcons?.get(0)?:"")
           }
    }

    private fun checkSmsPermission() {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                AlertDialog.Builder(this).setTitle("External storage permission")
                    .setMessage("This app requires access to External storage")
                    .setPositiveButton("Ask me"){
                            dialog, which -> requestPermission()
                    }
                    .setNegativeButton("Not now"){
                            dialog, which ->  notifyUser(false)
                    }.show()
            }else{
                requestPermission()
            }
        }else{
            notifyUser(true)
        }
    }
    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),PERMISSION_SEND_SMS)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_SEND_SMS ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    notifyUser(true)
                }else{
                    notifyUser(false)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun notifyUser(permissionGranted: Boolean) {
            if(true){
                //will continue
            }else{
                Toast.makeText(this,"This app requires access to External storage",Toast.LENGTH_SHORT).show()
                finish()
            }
    }
    private fun updateAdapter(it: List<CountryDetails>) {
        if(it.isNotEmpty()){
            progressBar.visibility=View.GONE
            nodataa.visibility=View.GONE
            recyclerView.visibility=View.VISIBLE
            recyclerView.apply {
                layoutManager = GridLayoutManager(this@MainActivity,2)
                adapter = CountryListAdapter(this@MainActivity, it)
            }
        }else{
            recyclerView.visibility=View.GONE
            progressBar.visibility=View.GONE
            nodataa.visibility=View.VISIBLE
        }

    }
}