package com.example.listingapp

import android.app.Application
import com.example.listingapp.roomdata.AppDatabase

class AppState:Application() {
    companion object{
        lateinit var  appDatabase:AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.invoke(applicationContext)
//        Stetho.initializeWithDefaults(this)
    }
}