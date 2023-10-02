package com.example.stela_android.Retrofit.Petugas.ProfilePetugas

import com.example.stela_android.Petugas.ProfilePetugas
import com.example.stela_android.Retrofit.Petugas.idTiket
import com.google.gson.annotations.SerializedName

data class ProfileResponse (

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: Profile? = null
)