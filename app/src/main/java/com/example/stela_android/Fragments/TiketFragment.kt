package com.example.stela_android.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.*
import com.example.stela_android.Ticket.Ticket
import kotlinx.android.synthetic.main.fragment_tiket.*
import kotlinx.android.synthetic.main.fragment_tiket.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TiketFragment: Fragment(), OnTicketClickListener {

    private val list = ArrayList<Tiket>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = this.arguments
        return inflater.inflate(R.layout.fragment_tiket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTickets()
    }

    private fun getTickets() {
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(TiketApi::class.java)
            retro.getTickets().enqueue(object : Callback<TiketResponse> {
                override fun onResponse(
                    call: Call<TiketResponse>,
                    response: Response<TiketResponse>
                ) {
                    response.body()?.data?.let { list.addAll(it) }
                    if (response.body()?.success == null) {
                        container_tiket.visibility = View.GONE
                        tv_empty_tiket.visibility = View.VISIBLE
                    } else {
                        tv_empty_tiket.visibility = View.GONE
                        rvTicketFragment.apply {
                            layoutManager = LinearLayoutManager(activity)
                            adapter = TiketAdapter(
                                context,
                                list,
                                this@TiketFragment
                            )
                            val ticketAdapter = adapter
                            rvTicketFragment.adapter = ticketAdapter
                            ticketAdapter?.notifyDataSetChanged()
                        }
                    }
                }
                override fun onFailure(call: Call<TiketResponse>, t: Throwable) {
                    Log.d("Ticket", "onFailure: " + t.message)
                }
            })
    }

    override fun onTicketItemClicked(position: Int) {
        val intent = Intent(activity, Ticket::class.java)
        intent.putExtra("id", list[position]?.id)
        intent.putExtra("no_tiket", list[position]?.no_tiket)
        intent.putExtra("judul", list[position]?.keterangan)
        startActivity(intent)
    }
}