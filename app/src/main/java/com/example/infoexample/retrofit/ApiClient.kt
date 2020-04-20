package com.com.example.infoexample.retrofit

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    val mServerURL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

    private var retrofit: Retrofit? = null
    val token = ""

    val client: Retrofit
        get() {
            if (retrofit == null) {
                val headerAuthorizationInterceptor = Interceptor { chain ->
                    var request: okhttp3.Request = chain.request()
                    val headers = request.headers().newBuilder().add("Authorization", "Bearer $token").build()
                    request = request.newBuilder().headers(headers).build()
                    chain.proceed(request)
                }
                val client = OkHttpClient.Builder()
                    .addInterceptor(headerAuthorizationInterceptor)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                retrofit = Retrofit.Builder()
                    .baseUrl(mServerURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()
            }
            return this.retrofit!!
        }
}