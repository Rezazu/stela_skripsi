package com.example.stela_android.Fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.OnTicketClickListener
import com.example.stela_android.Retrofit.Ticket.Rating.RatingResponse
import com.example.stela_android.Retrofit.Ticket.TiketApi
import com.example.stela_android.Retrofit.Ticket.TiketResponse
import kotlinx.android.synthetic.main.rate_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogRate(context:Context, id: Int): Dialog(context), OnTicketClickListener {

    init {
        setCancelable(false)
    }

    private var idTiket = id
    private var ratingTiket: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.rate_dialog)

        btnCloseListener()
        btnBeriNilaiListener()
    }

    fun btnCloseListener() {
        btn_close.setOnClickListener {
            this.dismiss()
            Log.d("Success", "btn close clicked")
        }
    }

    fun btnBeriNilaiListener() {
        rating_bar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if(rating <= 1) {
                rating_image.setImageResource(R.drawable.rate_1)
            } else if(rating <= 2) {
                rating_image.setImageResource(R.drawable.rate_2)
            } else if(rating <= 3) {
                rating_image.setImageResource(R.drawable.rate_3)
            } else if(rating <= 4) {
                rating_image.setImageResource(R.drawable.rate_4)
            } else if(rating <= 5) {
                rating_image.setImageResource(R.drawable.rate_5)
            }
            ratingTiket = rating
        }

        btn_beri_nilai.setOnClickListener {
            Log.d("Success", "btn $idTiket clicked, with rating : $ratingTiket")
            val prefs = context?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
            val token = prefs?.getString("token", "")
            val retro = Retrofit.getRetroData(token!!).create(TiketApi::class.java)

            retro.giveRating(idTiket, ratingTiket?.toInt()).enqueue(object: Callback<RatingResponse> {
                override fun onResponse(call: Call<RatingResponse>, response: Response<RatingResponse>) {
                    Log.d("Success", "onRated: " + response?.body()?.message)
                    dismiss()
                }

                override fun onFailure(call: Call<RatingResponse>, t: Throwable) {
                    Log.d("Rating", "onFailure: " + t.message)
                }

            })
        }
    }

    override fun onTicketItemClicked(position: Int) {
    }
}