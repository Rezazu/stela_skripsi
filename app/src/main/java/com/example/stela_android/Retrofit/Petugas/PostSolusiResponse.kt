package com.example.stela_android.Retrofit.Petugas

import com.google.gson.annotations.SerializedName

data class PostSolusiResponse (
    @field:SerializedName("data")
    val data: idTiket? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,
)

data class idTiket(
    @field:SerializedName("id_tiket")
    val id_tiket: String? = null,
)