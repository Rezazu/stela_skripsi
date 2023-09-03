package com.example.stela_android.Retrofit.Petugas

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UpdateSelesaiApi {
    @POST("update-selesai")
    fun updateSolusi(
        @Body body: RequestBody,
    ): Call<PostSolusiResponse>

    @POST("update-terkendala")
    fun updateTerkendala(
        @Body body: RequestBody,
    ): Call<PostSolusiResponse>
}