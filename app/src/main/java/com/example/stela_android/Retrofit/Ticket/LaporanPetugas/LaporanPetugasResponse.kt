package com.example.stela_android.Retrofit.Ticket.LaporanPetugas

import com.google.gson.annotations.SerializedName

data class LaporanPetugasResponse (

    @field:SerializedName("id_dokumen")
    val id_dokumen: String?,

    @field:SerializedName("original_name")
    val original_name: String,

    @field:SerializedName("image_name")
    val image_name: String,

    @field:SerializedName("path")
    val path: String,

    )