package com.example.stela_android.Retrofit.Ticket

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface TicketApi {

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("tiket")
    fun getTickets(): Call<TiketResponse>

}