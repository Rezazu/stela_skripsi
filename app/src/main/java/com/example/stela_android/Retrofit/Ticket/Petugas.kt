package com.example.stela_android.Retrofit.Ticket

import com.google.gson.annotations.SerializedName

data class Petugas (

        @field:SerializedName("id")
        val id: Int? = 0,

        @field:SerializedName("nama")
        val nama: String? = null,

        @field:SerializedName("rating")
        val rating: Float? = 0.00f,

        )