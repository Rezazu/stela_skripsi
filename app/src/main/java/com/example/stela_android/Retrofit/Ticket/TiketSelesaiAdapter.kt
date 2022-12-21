package com.example.stela_android.Retrofit.Ticket

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Fragments.DialogRate
import com.example.stela_android.R
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.rate_dialog.view.*
import kotlinx.android.synthetic.main.ticket_item.view.*
import kotlinx.android.synthetic.main.ticket_item.view.rating_bar


class TiketSelesaiAdapter(private val context: Context, private val list: ArrayList<Tiket>, private val onTicketClickListener: OnTicketClickListener): RecyclerView.Adapter<TiketSelesaiAdapter.TicketViewHolder>() {
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

                // setting displaying of ticket's date is in bottom right corner if status is not 6 and rating is null
                if(statusTiket == 6 && ratingTiket != null) {
                    // displaying tanggal tiket
                    status_tiket.text = tanggalTiket
                    // hide rating bar
                    rating_bar.visibility = View.VISIBLE
                    rating_bar.rating = ratingTiket.toFloat()
                    ll_ticket_success.visibility = View.GONE
                    tanggal_tiket.visibility = View.GONE
                    ticket.setBackground(resources.getDrawable(R.drawable.border_gray))
                } else {
                    if(statusTiket != 6  && ratingTiket == null) {
                        ticket.visibility = View.GONE
                        ticket.layoutParams.width = 0
                        ticket.layoutParams.height = 0
                    } else {
                        ticket.visibility = View.GONE
                        ticket.layoutParams.width = 0
                        ticket.layoutParams.height = 0
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