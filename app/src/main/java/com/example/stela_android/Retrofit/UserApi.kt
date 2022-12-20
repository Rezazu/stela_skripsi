package com.example.stela_android.Retrofit

import com.example.stela_android.Retrofit.Form.PostPermintaanResponse
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Part
import retrofit2.http.Multipart
import java.io.File

interface UserApi {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
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