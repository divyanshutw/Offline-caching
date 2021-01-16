package com.practice.offlinecaching

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class CarsViewModel(application: Application, val type: String?, val token:String?) : AndroidViewModel(
    application)
{

    private val viewModelJob= Job()
    private val viewModelScope= CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database= getDatabase(application)
    private val carsRepository=CarsRepository(database)

    var isRepositoryEmpty=MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            carsRepository.refreshCars(type,token)
        }
    }

    var isSavingToDatabase=MutableLiveData<Boolean>()
    var isCarSavedToDatabase:Boolean=false
    var isSavedToLocal=MutableLiveData<Boolean>()

    fun refresh()
    {
        viewModelScope.launch {
            carsRepository.refreshCars(type,token)
        }
    }

    val carsTypeList=carsRepository.getCars(type)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun uploadData(data:Cars) {
        //Call the put or post API to put the data
        if(/*if the data is successfully updated in the remote database*/true) {
            isCarSavedToDatabase = true
            isSavingToDatabase.value = false
        }
    }

}