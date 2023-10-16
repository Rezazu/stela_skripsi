package com.example.stela_android.Retrofit.Ticket

import com.example.stela_android.Retrofit.Petugas.PostPenggunaResponse
import com.example.stela_android.Retrofit.Ticket.Rating.RatingResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface TiketApi {

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("tiket")
    fun getTickets(): Call<TiketResponse>

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")

    @GET("tiket")
    fun getTicketsByCategory(
        @Query("kategori") kategori: Int,
    ): Call<TiketResponse>

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("tiket")
    fun getTicketsById(
        @Query("id") id: Int,
    ): Call<TiketResponseById>

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("tiket")
    fun getTicketsByNoTiket(
        @Query("no_tiket") no_tiket: String,
    ): Call<TiketResponseById>

    @POST("update-pengguna")
    fun updateSelesaiPengguna(
        @Body body: RequestBody,
    ): Call<PostPenggunaResponse>

    @GET("petugas/{id}")
    fun getPetugasById(
        @Query("id") id: Int,
    ): Call<ListPetugasResponse>

    @Headers("Content-type: application/json",
        "Authorization: Bearer my token")
    @GET("tiket")
    fun getTicketsByCategoryAndStatus(
        @Query("kategori") kategori: Int,
        @Query("status") status: Int,
    ): Call<TiketResponse>

    @FormUrlEncoded
    @POST("rating/id/{id}")
    fun giveRating (
        @Path("id") id: Int,
        @Field("rating") rating: Int?,
        @Field("ratingKeterangan") ratingKeterangan: String?,
    ): Call<RatingResponse>
}