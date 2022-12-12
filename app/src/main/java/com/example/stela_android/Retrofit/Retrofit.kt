package com.example.stela_android.Retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    val BASE_URL = "http://192.168.0.107:8000/api/"

    fun getRetroClientInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor(
                "Bearer",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJuYW1hX2xlbmdrYXAiOiJ1c2VyMSIsInVzZXJuYW1lIjoidXNlcjEiLCJlbWFpbCI6InVzZXIxQGdtYWlsLmNvbSIsInN0YXR1cyI6IjEiLCJrZF9kZXBhcnRlbWVuIjpudWxsLCJiYWdpYW4iOm51bGwsImdlZHVuZyI6bnVsbCwidW5pdF9rZXJqYSI6bnVsbCwicnVhbmdhbiI6bnVsbCwibGFudGFpIjpudWxsLCJ0ZWxlcG9uIjpudWxsLCJocCI6bnVsbCwicGVyYW4iOlsidmVyaWZpY2F0b3IiLCJoZWxwZGVzayJdLCJleHAiOjE2NzA4NzE4Mzh9.WprBAEWHKmNp6r_OEBDfuK-O1MSPiCzmdSVPKZg7FyU"

            )).addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}