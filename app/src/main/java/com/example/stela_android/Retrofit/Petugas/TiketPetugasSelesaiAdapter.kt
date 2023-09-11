package com.example.stela_android.Retrofit.Petugas

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Fragments.DialogRate
import com.example.stela_android.Petugas.Tiket.TiketPetugasSelesaiFragment
import com.example.stela_android.R
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.activity_ticket_petugas.view.*
import kotlinx.android.synthetic.main.ticket_item.view.*
import kotlinx.android.synthetic.main.ticket_item.view.judul_tiket
import kotlinx.android.synthetic.main.ticket_petugas_item.view.*


class TiketPetugasSelesaiAdapter(private val context: Context, private val list: ArrayList<TiketPetugas>, private val onTicketClickListener: TiketPetugasSelesaiFragment): RecyclerView.Adapter<TiketPetugasSelesaiAdapter.TicketViewHolder>() {
    inner class TicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(ticketResponse: TiketPetugas) {
            with(itemView) {
                // get all data ticket
                var judul = ticketResponse.keterangan
                val ratingTiket = ticketResponse.rating
                val noTiket = ticketResponse.no_tiket
                val statusTiket = ticketResponse.id_status_tiket
                val tanggalInputTiket = ticketResponse.tanggal

                // get container of item
                val rvTiket: RelativeLayout = findViewById(R.id.ticket_petugas)
                // get tv status tiket
                var tvStatusTiket: TextView = findViewById(R.id.status_tiket_petugas)
                // get tv kode tiket
                var tvKodeTiket: TextView = findViewById(R.id.kode_tiket)
                // get tv tanggal tiket
                var tvTanggalTiket: TextView = findViewById(R.id.tanggal_tiket_petugas)


                tvKodeTiket.text = noTiket.toString()
                tvTanggalTiket.text = tanggalInputTiket.toString()
                Service.statusTiketDisplay(statusTiket, tvStatusTiket)

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
//                    status_tiket_petugas.text = tanggalTiket
                    // hide rating bar
                    rating_bar_petugas_selesai.visibility = View.VISIBLE
                    rating_bar_petugas_selesai.rating = ratingTiket.toFloat()
                    belum_dinilai.visibility = View.GONE
                    tvTanggalTiket.visibility = View.GONE
                    ticket_petugas.setBackground(resources.getDrawable(R.drawable.border_gray))
                } else {
                    if(statusTiket != 6  && ratingTiket == null) {
                        ticket_petugas.visibility = View.GONE
                        ticket_petugas.layoutParams.width = 0
                        ticket_petugas.layoutParams.height = 0
                    } else {
                        ticket_petugas.visibility = View.GONE
                        ticket_petugas.layoutParams.width = 0
                        ticket_petugas.layoutParams.height = 0
                    }
                }

                kode_tiket.text = noTiket

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_petugas_item, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onTicketClickListener.onTicketPetugasItemClicked(position)
        }

    }

    override fun getItemCount(): Int = list.size

}