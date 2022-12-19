package com.example.stela_android.Retrofit.Notification

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface NotificationApi {
    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("notifikasi")
    fun getNotifikasi(
    ): Call<NotificationResponse>
}