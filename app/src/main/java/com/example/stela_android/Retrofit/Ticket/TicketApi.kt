package com.example.stela_android.Retrofit.Ticket

import retrofit2.Call
import retrofit2.http.GET

interface TicketApi {

    @GET("tiket")
    fun getTickets(): Call<TicketResponse>

}