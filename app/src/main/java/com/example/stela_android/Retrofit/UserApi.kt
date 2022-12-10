package com.example.stela_android.Retrofit

import retrofit2.Call
import retrofit2.http.*


interface UserApi {
    @POST("login")
    fun login (
        @Body userRequest: UserRequest
    ): Call<UserResponse>



}