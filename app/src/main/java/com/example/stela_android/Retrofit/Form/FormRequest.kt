package com.example.stela_android.Retrofit.Form

import android.text.Editable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FormRequest (
    val bagian: String,
    val gedung: String,
    val ruangan: String,
    val lantai: String,
    val keterangan: String,
)