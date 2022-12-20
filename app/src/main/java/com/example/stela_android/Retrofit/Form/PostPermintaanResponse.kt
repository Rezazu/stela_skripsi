package com.example.stela_android.Retrofit.Form

import com.google.gson.annotations.SerializedName

data class PostPermintaanResponse (

    @field:SerializedName("success")
    val bagian: Boolean? = null,

    @field:SerializedName("message")
    val gedung: String? = null,

    @field:SerializedName("data")
    val data: nomorTiket? = null
)

data class nomorTiket(
    @field:SerializedName("no_tiket")
    val no_tiket: String? = null
)