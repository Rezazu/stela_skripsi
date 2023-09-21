package com.example.stela_android.Retrofit.Petugas

import com.example.stela_android.Retrofit.Petugas.ProfilePetugas.ProfileResponse
import com.example.stela_android.Retrofit.Ticket.TiketResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PetugasTiketApi {

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("helpdesk/permintaan")
    fun getPermintaan(): retrofit2.Call<PermintaanResponse>

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("profile")
    fun getProfile(): retrofit2.Call<ProfileResponse>

}