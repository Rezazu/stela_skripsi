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


class TiketAdapter(private val context: Context, private val list: ArrayList<Tiket>, private val onTicketClickListener: OnTicketClickListener): RecyclerView.Adapter<TiketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(ticketResponse: Tiket) {
            with(itemView) {
                // get all data ticket
                var judul = ticketResponse.keterangan
                val ratingTiket = ticketResponse.rating
                val noTiket = ticketResponse.no_tiket
                val statusTiket = ticketResponse.id_status_tiket
                val tanggalInputTiket = ticketResponse.tanggal_input
                val idKategori = ticketResponse.id_kategori

                // get container of item
                val rvTiket: RelativeLayout = findViewById(R.id.ticket)
                // get tv status tiket
                val tvStatusTiket: TextView = findViewById(R.id.status_tiket)
                val iconKategori: ImageView = findViewById(R.id.iv_kategori)

                // checking if judul which is taken through keterangan is not bigger than equal 35
                if(judul?.length!! >= 35) {
                    judul = Service.judulSubStr(judul)
                    judul_tiket.text = "$judul ..."
                } else {
                    judul_tiket.text = judul
                }

                if (idKategori == 1){
                    iconKategori.setBackgroundResource(R.drawable.icon_sistem_informasi)
                } else if (idKategori == 2){
                    iconKategori.setBackgroundResource(R.drawable.icon_infrastruktur)
                } else if (idKategori == 3){
                    iconKategori.setBackgroundResource(R.drawable.icon_tata_kelola)
                } else if (idKategori == 4) {
                    iconKategori.setBackgroundResource(R.drawable.icon_lainnya)
                } else {
                    iconKategori.setBackgroundResource(R.drawable.icon_tidak_kategori)
                }

                // setting timestamp retrieved to the format asked
                val tanggalTiket = Service.date(tanggalInputTiket)

                // setting border of ticket container based on its category

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
                    tv_selesai_mandiri.visibility = View.GONE
                } else {
                    // hide tanggal tiket on bottom right cornere
                    tanggal_tiket.visibility = View.GONE
                    // setting if ticket has been rated, so display the stars not btn rate ticket
                    if(statusTiket == 6 && ratingTiket != null) {
                        ticket.visibility = View.GONE
                        ticket.layoutParams.width = 0
                        ticket.layoutParams.height = 0
                        // hide ticket success section
                        ll_ticket_success.visibility = View.GONE
                        // displaying rating bar
                        rating_bar.visibility = View.VISIBLE
                        rating_bar.rating = ratingTiket.toFloat()
                        // displaying status tiket as tanggal tiket
                        tvStatusTiket.text = tanggalTiket.toString()
                        tv_selesai_mandiri.visibility = View.GONE
                    } else if (statusTiket == 6 && idKategori == null) {
                        ticket.visibility = View.GONE
                        ticket.layoutParams.width = 0
                        ticket.layoutParams.height = 0
                        tv_selesai_mandiri.visibility = View.GONE
                    }
                    else {
                        // displaying status tiket as tanggal tiket
                        tv_selesai_mandiri.visibility = View.GONE
                        tvStatusTiket.text = tanggalTiket.toString()
                        // displaying ticket success section
                        ll_ticket_success.visibility = View.VISIBLE
                        // hide rating bar
                        rating_bar.visibility = View.GONE
                        tv_selesai_mandiri.visibility = View.GONE
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