package com.example.stela_android.Retrofit.Petugas

import com.google.gson.annotations.SerializedName

data class PostPenggunaResponse (
    @field:SerializedName("data")
    val data: idTiketPengguna? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,
)

data class idTiketPengguna(
    @field:SerializedName("id_tiket")
    val id_tiket: String? = null,
)