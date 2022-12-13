package com.example.stela_android.Retrofit.Ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import kotlinx.android.synthetic.main.ticket_item.view.*

class TicketAdapter(private val list: ArrayList<TiketResponse>): RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(aTicketResponse: TiketResponse) {
            with(itemView) {
                // Data Header Tiket
                val keteranganTiket = aTicketResponse.data?.keterangan
                val noTiket = aTicketResponse.data?.no_tiket
                val statusTiket = aTicketResponse.data?.status

                // Data Pelapor
                val nama_pelapor = aTicketResponse.data?.nama_pelapor
                val jabatan_pelapor = aTicketResponse.data?.bagian_pelapor
                val unit_kerja_pelapor = aTicketResponse.data?.unit_kerja_pelapor
                val gedung_pelapor = aTicketResponse.data?.gedung_pelapor
                val lantai_pelapor = aTicketResponse.data?.lantai_pelapor
                val ruangan_pelapor = aTicketResponse.data?.ruangan_pelapor

                // Data Lengkap Tiket
                val judul = aTicketResponse.data?.keterangan
                val deskripsi = aTicketResponse.data?.keterangan
                val tanggalTiket = aTicketResponse.data?.tanggal_input

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
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = 0

}