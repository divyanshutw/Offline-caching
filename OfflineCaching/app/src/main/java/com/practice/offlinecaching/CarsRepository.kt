package com.practice.offlinecaching

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject


class CarsRepository(private val database: CarsDatabase)
{

    fun getCars(key:String?): LiveData<List<Cars>> {
        return database.CarsDatabaseDao.getCarsFromRoom(key!!)
    }

    suspend fun refreshCars(type: String?,token:String?)
    {
        withContext(Dispatchers.IO){
            var url=""
            AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        if (response != null) {


                            var carsListData = response.get("data") as JSONArray
                            val list = ArrayList<Cars>()
                            var i = 0
                            while (i < carsListData.length()) {
                                //Insert the data into the list
                            }
                            AsyncTask.execute {
                                database.CarsDatabaseDao.insertCarsToRoom(list)
                            }
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.d("div", "CarsRepository L49 API error: ${anError?.response} --- ${anError?.errorCode} --- ${anError?.errorBody} --- ${anError?.errorDetail} ")

                    }

                })

        }
    }
}