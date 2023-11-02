package com.example.stela_android.Retrofit

import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Headers(
        "Content-type: application/json",
        "Authorization: Bearer my token"
    )
    @GET("user")
    fun getUser(
    ): Call<LoginResponse>
}