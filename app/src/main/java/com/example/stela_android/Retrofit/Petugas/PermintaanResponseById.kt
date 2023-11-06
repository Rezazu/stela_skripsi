package com.example.stela_android.Retrofit.Petugas

import com.google.gson.annotations.SerializedName

data class PermintaanResponseById(
    @field:SerializedName("data")
    val data: TiketPetugas? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    )