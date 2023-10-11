package com.example.stela_android.Retrofit.Petugas.ProfilePetugas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Petugas.DaftarRating
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Petugas.TiketPetugas
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.rating_item.view.*
import kotlinx.android.synthetic.main.ticket_petugas_item.view.*

class DaftarRatingAdapter(private val context: Context?, private val list: ArrayList<TiketPetugas>, private val daftarRating: DaftarRating) : RecyclerView.Adapter<DaftarRatingAdapter.TicketViewHolder>() {
    inner class TicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(ticketResponse: TiketPetugas){
            with(itemView){
                //Get data rating
                val tanggalTiket = ticketResponse.tanggal
                val ratingTiket = ticketResponse.rating
                val keteranganRating = ticketResponse.keterangan_rating
                val pelaporTiket = ticketResponse.nama_pelapor
                val nomorTiket = ticketResponse.no_tiket
                val statusTiket = ticketResponse.id_status_tiket

                tv_tanggal_rating.text = Service.datePetugas(tanggalTiket)
                tv_no_tiket.text = nomorTiket

                if (statusTiket == 6 && ratingTiket !=null) {
                    rating_bar_item.rating = ratingTiket.toFloat()
                    tv_keterangan_rating.text = "'$keteranganRating'"
                    tv_nama_rating.text = "- " + pelaporTiket
                } else {
                    rating_item.visibility = View.GONE
                    rating_item.layoutParams.width = 0
                    rating_item.layoutParams.height = 0
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rating_item, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}
