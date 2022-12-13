package com.example.stela_android.Retrofit.Ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import kotlinx.android.synthetic.main.ticket_item.view.*

class TicketAdapter(private val list: TicketResponse): RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(ticketResponse: TicketResponse) {
            with(itemView) {
                // Data Header Tiket
                val keteranganTiket = ticketResponse.data?.keterangan
                val noTiket = ticketResponse.data?.no_tiket
                val statusTiket = ticketResponse.data?.status

                // Data Pelapor
                val nama_pelapor = ticketResponse.data?.nama_pelapor
                val jabatan_pelapor = ticketResponse.data?.bagian_pelapor
                val unit_kerja_pelapor = ticketResponse.data?.unit_kerja_pelapor
                val gedung_pelapor = ticketResponse.data?.gedung_pelapor
                val lantai_pelapor = ticketResponse.data?.lantai_pelapor
                val ruangan_pelapor = ticketResponse.data?.ruangan_pelapor

                // Data Lengkap Tiket
                val judul = ticketResponse.data?.keterangan
                val deskripsi = ticketResponse.data?.keterangan
                val tanggalTiket = ticketResponse.data?.tanggal_input

                keterangan_tiket.text = judul
                no_tiket.text = noTiket
                status_tiket.text = statusTiket.toString()
                tanggal_tiket.text = tanggalTiket.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_item, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        //
    }

    override fun getItemCount(): Int = 0

}