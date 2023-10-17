package com.example.stela_android.Retrofit.Notification

import retrofit2.Call
import retrofit2.http.*

interface NotificationApi {
    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("notifikasi")
    fun getNotifikasi(
    ): Call<NotificationResponse>

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @PUT("read-notifikasi/id_notif/{id_notif}")
    fun readNotifikasi(
        @Path("id_notif") id_notif: Int,
    ): Call<NotificationResponse>
}