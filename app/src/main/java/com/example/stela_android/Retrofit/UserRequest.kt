package com.example.stela_android.Retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRequest {
    @SerializedName("username")
    @Expose
    var email:String? = null

    @SerializedName("password")
    @Expose
    var password:String? = null

    @SerializedName("token")
    @Expose
    var token:String? = null
}