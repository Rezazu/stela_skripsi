package com.example.stela_android.Retrofit.Ticket

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Fragments.DialogRate
import com.example.stela_android.Fragments.TiketSelesaiFragment
import com.example.stela_android.R
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.ticket_item.view.*
import kotlinx.android.synthetic.main.ticket_item.view.rating_bar


class TiketSelesaiAdapter(private val context: Context, private val list: ArrayList<Tiket>, private val onTicketClickListener: TiketSelesaiFragment): RecyclerView.Adapter<TiketSelesaiAdapter.TicketViewHolder>() {
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

                // setting displaying of ticket's date is in bottom right corner if status is not 6 and rating is null
                if(statusTiket == 6 && ratingTiket != null) {
                    Service.statusTiketDisplay(statusTiket, tvStatusTiket)
                    rating_bar.visibility = View.VISIBLE
                    rating_bar.rating = ratingTiket.toFloat()
                    ll_ticket_success.visibility = View.GONE
                    tanggal_tiket.text = tanggalTiket
                    ticket.setBackground(resources.getDrawable(R.drawable.border_gray2))
                    tv_selesai_mandiri.visibility = View.GONE
                } else {
                    if(statusTiket != 6  && ratingTiket == null) {
                        ticket.visibility = View.GONE
                        ticket.layoutParams.width = 0
                        ticket.layoutParams.height = 0
                        tv_selesai_mandiri.visibility = View.GONE
                    } else if (statusTiket == 6 && idKategori == null) {
                        ticket.visibility = View.VISIBLE
                        tv_selesai_mandiri.visibility = View.VISIBLE
                        rating_bar.visibility = View.GONE
                        btn_nilai.visibility = View.GONE
                    }
                    else {
                        ticket.visibility = View.GONE
                        ticket.layoutParams.width = 0
                        ticket.layoutParams.height = 0
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