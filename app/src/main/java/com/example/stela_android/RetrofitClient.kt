package com.example.stela_android

import com.example.stela_android.Retrofit.Api
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }

    fun getRetroClientInstance(): Retrofit{
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}