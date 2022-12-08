package com.example.stela_android.Login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("data")
    @Expose
    var data: User? = null

    class User{
        @SerializedName("username")
        @Expose
        var username: String? = null

        @SerializedName("password")
        @Expose
        var password: String? = null

        @SerializedName("token")
        @Expose
        var token: String? = null
    }
}