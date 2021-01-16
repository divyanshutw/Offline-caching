package com.practice.offlinecaching

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CarsViewModelFactory(val app:Application, val type:String?, val token:String?) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarsViewModel(app,type,token) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}