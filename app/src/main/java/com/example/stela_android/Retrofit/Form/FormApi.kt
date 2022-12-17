package com.example.stela_android.Retrofit.Form

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.*

interface FormApi {

    @GET("permintaan")
    fun getPermintaan(
        @Field("id") id: String
    ): Call<PostResponse>

    @Multipart
    @POST("permintaan")
    fun createPermintaan(
        @Part("bagian") bagian: String,
        @Part("gedung") gedung: String,
        @Part("ruangan") ruangan: String,
        @Part("lantai") lantai: String,
        @Part("keterangan") keterangan: String,
        @Part dokumen: Array<MultipartBody.Part>
    ): Call<PostResponse>
}