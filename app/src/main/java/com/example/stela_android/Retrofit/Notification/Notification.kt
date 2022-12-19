package com.example.stela_android.Retrofit.Notification

import com.google.gson.annotations.SerializedName

data class Notification (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_pengguna")
    val id_pengguna: Int? = null,

    @field:SerializedName("no_tiket")
    val no_tiket: String? = null,

    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("dibaca")
    val dibaca: Int? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,
)