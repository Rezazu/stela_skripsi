package com.example.stela_android.Retrofit.Form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FormRequest (
    @SerializedName("id")
    @Expose
    var id:String? = null
)