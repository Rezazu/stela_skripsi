package com.example.stela_android.Retrofit.Petugas

import com.example.stela_android.Retrofit.Ticket.TiketResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface PetugasTiketApi {

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("helpdesk/permintaan")
    fun getPermintaan(): retrofit2.Call<PermintaanResponse>

//    @Headers("Content-type: application/json",
//        "Authorization: Bearer my token")
//    @GET("servicedesk/permintaan")
//    fun getPermintaan(): Call<TiketResponse>
}