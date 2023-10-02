package com.example.stela_android.Retrofit.Petugas.ProfilePetugas

import com.google.gson.annotations.SerializedName

data class Profile (
    @field:SerializedName("id_petugas")
    val id_petugas: Int? = 0,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("jumlah_tiket")
    val jumlah_tiket: Int? = null,

    @field:SerializedName("jumlah_tiket_nilai")
    val jumlah_tiket_nilai: Int? = null,

    @field:SerializedName("rating")
    val rating: Float? = 0.00f,

    @field:SerializedName("status")
    val status: String? =null,
)