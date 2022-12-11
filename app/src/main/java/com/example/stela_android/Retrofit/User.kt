package com.example.stela_android.Retrofit

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("unit_kerja")
    val unitKerja: String? = null,

    @field:SerializedName("nama_lengkap")
    val namaLengkap: String? = null,

    @field:SerializedName("telepon")
    val telepon: String? = null,

    @field:SerializedName("hp")
    val hp: String? = null,

    @field:SerializedName("gedung")
    val gedung: String? = null,

    @field:SerializedName("bagian")
    val bagian: String? = null,

    @field:SerializedName("ruangan")
    val ruangan: String? = null,

    @field:SerializedName("peran")
    val peran: MutableSet<String>? = null,

    @field:SerializedName("lantai")
    val lantai: String? = null,

    @field:SerializedName("kd_departemen")
    val kdDepartemen: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)