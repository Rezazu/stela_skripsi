package com.example.stela_android.Retrofit.Petugas

import com.google.gson.annotations.SerializedName

data class Solusi (
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("pelapor")
    val nama_pelapor: String? = null,

    @field:SerializedName("id_status_tiket")
    val id_status_tiket: Int? = null,

    @field:SerializedName("id_status_tiket_internal")
    val id_status_tiket_internal: Int? = null,

    @field:SerializedName("keterangan_tiket")
    val kterangan_tiket: String? =null
    )