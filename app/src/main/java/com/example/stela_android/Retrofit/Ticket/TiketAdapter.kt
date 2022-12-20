package com.example.stela_android.Retrofit.Ticket

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Fragments.DialogRate
import com.example.stela_android.R
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.ticket_item.view.*


class TiketAdapter(private val context: Context, private val list: ArrayList<Tiket>, private val kategori: String?, private val onTicketClickListener: OnTicketClickListener): RecyclerView.Adapter<TiketAdapter.TicketViewHolder>() {
    inner class TicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(ticketResponse: Tiket) {
            with(itemView) {
                // get all data ticket
                var judul = ticketResponse.keterangan
                val ratingTiket = ticketResponse.rating
                val noTiket = ticketResponse.no_tiket
                val statusTiket = ticketResponse.id_status_tiket
                val tanggalInputTiket = ticketResponse.tanggal_input

                // get container of item
                val rvTiket: RelativeLayout = findViewById(R.id.ticket)
                // get tv status tiket
                val tvStatusTiket: TextView = findViewById(R.id.status_tiket)

                // checking if judul which is taken through keterangan is not bigger than equal 35
                if(judul?.length!! >= 35) {
                    judul = Service.judulSubStr(judul)
                    judul_tiket.text = "$judul ..."
                } else {
                    judul_tiket.text = judul
                }

                // setting timestamp retrieved to the format asked
                val tanggalTiket = Service.date(tanggalInputTiket)

                // setting border of ticket container based on its category
                if(kategori == "Sistem Informasi") {
                    rvTiket.background = resources.getDrawable(R.drawable.border_blue)
                } else if(kategori == "Infrastruktur Jaringan") {
                    rvTiket.background = resources.getDrawable(R.drawable.border_red)
                } else if(kategori == "Tata Kelola TI") {
                    rvTiket.background = resources.getDrawable(R.drawable.border_green)
                } else if(kategori == "Lainnya") {
                    rvTiket.background = resources.getDrawable(R.drawable.border_purple)
                }

                // setting displaying of ticket's date is in bottom right corner if status is not 6 and rating is null
                if(statusTiket != 6 && ratingTiket == null) {
                    // displaying tanggal tiket
                    tanggal_tiket.text = tanggalTiket
                    // displaying status tiket on ticket_item
                    Service.statusTiketDisplay(statusTiket, tvStatusTiket)
                    // hide rating bar
                    rating_bar.visibility = View.GONE
                    // hide ticket success section
                    ll_ticket_success.visibility = View.GONE
                } else {
                    // hide tanggal tiket on bottom right cornere
                    tanggal_tiket.visibility = View.GONE
                    // setting if ticket has been rated, so display the stars not btn rate ticket
                    if(statusTiket == 6 && ratingTiket != null) {
                        // hide ticket success section
                        ll_ticket_success.visibility = View.GONE
                        // displaying rating bar
                        rating_bar.visibility = View.VISIBLE
                        rating_bar.rating = ratingTiket.toFloat()
                        // displaying status tiket as tanggal tiket
                        tvStatusTiket.text = tanggalTiket.toString()
                    } else {
                        // displaying status tiket as tanggal tiket
                        tvStatusTiket.text = tanggalTiket.toString()
                        // displaying ticket success section
                        ll_ticket_success.visibility = View.VISIBLE
                        // hide rating bar
                        rating_bar.visibility = View.GONE
                    }
                }

                no_tiket.text = noTiket

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_item, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onTicketClickListener.onTicketItemClicked(position)
        }
        holder.itemView.btn_nilai.setOnClickListener {
            val bundle = Bundle()
            DialogRate(context, list[position]?.id).show()
        }
    }

    override fun getItemCount(): Int = list.size

}