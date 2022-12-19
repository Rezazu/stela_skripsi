package com.example.stela_android.Retrofit.Notification

import com.example.stela_android.Retrofit.Data
import com.google.gson.annotations.SerializedName
import java.io.File

data class NotificationResponse (

    @field:SerializedName("sucess")
    val success: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: ArrayList<Notification>? = null

    )