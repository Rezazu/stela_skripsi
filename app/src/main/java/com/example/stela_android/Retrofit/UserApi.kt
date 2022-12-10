package com.example.stela_android.Retrofit

import retrofit2.Call
import retrofit2.http.*


interface UserApi {
    @FormUrlEncoded
    @POST("login")
    fun login (
        @Field("username") username:String,
        @Field("password") password:String
    ): Call<LoginResponse>



}