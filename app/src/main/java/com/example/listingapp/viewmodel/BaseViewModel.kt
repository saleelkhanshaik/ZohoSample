package com.example.listingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(), CoroutineScope {

    //will create Job instance
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job+ Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}