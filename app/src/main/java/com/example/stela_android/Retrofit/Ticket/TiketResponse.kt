package com.example.stela_android.Retrofit.Ticket

import com.google.gson.annotations.SerializedName

data class TiketResponse(

    @field:SerializedName("data")
    val data: ArrayList<Tiket>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    )
