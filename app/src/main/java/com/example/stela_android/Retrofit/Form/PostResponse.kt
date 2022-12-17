package com.example.stela_android.Retrofit.Form

import com.google.gson.annotations.SerializedName
import java.io.File

data class PostResponse (

    @field:SerializedName("bagian")
    val bagian: String? = null,

    @field:SerializedName("gedung")
    val gedung: String? = null,

    @field:SerializedName("ruangan")
    val ruangan: String? = null,

    @field:SerializedName("lantai")
    val lantai: String? = null,

    @field:SerializedName("dokumen")
    val dokumen: File? = null,

    @field:SerializedName("keterangan")
    val keterangan: String? = null,
)