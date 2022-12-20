package com.example.stela_android.Retrofit.Ticket.Rating

import com.google.gson.annotations.SerializedName

data class RatingResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

)
