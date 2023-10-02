package com.example.stela_android.Retrofit.Ticket

import com.google.gson.annotations.SerializedName

data class ListPetugasResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: ArrayList<Petugas>? = null,
    )