package com.example.infoexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.com.example.infoexample.retrofit.ApiClient
import com.com.example.infoexample.retrofit.ApiInterface
import com.example.infoexample.model.AboutModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository {
    private var apiInterface: ApiInterface

    companion object {
        private var sInstance: DataRepository? = null

        @Synchronized
        fun getInstance(): DataRepository {
            if (sInstance == null) {
                if (sInstance == null) {
                    sInstance = DataRepository()
                }
            }
            return sInstance!!
        }
    }

    init {
        apiInterface = ApiClient.client.create(ApiInterface::class.java)
    }

    fun getFacts(): LiveData<AboutModel> {
        val data = MutableLiveData<AboutModel>()
        apiInterface.getFacts().enqueue(object : Callback<AboutModel> {
            override fun onResponse(call: Call<AboutModel>, response: Response<AboutModel>) {
                data.setValue(response.body())
            }
            override fun onFailure(call: Call<AboutModel>, t: Throwable) {
                data.setValue(null)
            }
        })
        return data
    }

}
