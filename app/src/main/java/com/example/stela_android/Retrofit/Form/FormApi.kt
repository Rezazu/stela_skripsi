package com.example.stela_android.Retrofit.Form

import android.text.Editable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.internal.io.FileSystem
import retrofit2.Call
import retrofit2.http.*
import java.io.File


interface FormApi {
    @POST("permintaan")
    fun createPermintaan(
        @Body body: RequestBody
    ): Call<PostPermintaanResponse>
}