package com.com.example.infoexample.retrofit


import com.example.infoexample.model.AboutModel
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiInterface {

    companion object {
        const val APIPath = ""
        const val HeadersToken = "Accept:application/json"
    }


    @Headers(HeadersToken)
    @GET("facts.json")
    fun getFacts(): Call<AboutModel>

}
