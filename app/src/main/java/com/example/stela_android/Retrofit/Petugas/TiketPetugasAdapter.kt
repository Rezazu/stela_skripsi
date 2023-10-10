package com.example.stela_android.Retrofit.Petugas

import android.content.Context
import android.media.Image
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Petugas.Tiket.TiketPetugasFragment
import com.example.stela_android.R
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.ticket_item.view.*
import kotlinx.android.synthetic.main.ticket_item.view.judul_tiket
import kotlinx.android.synthetic.main.ticket_petugas_item.view.*


class TiketPetugasAdapter(private val context: Context, private val list: ArrayList<TiketPetugas>, private val onTicketPetugasClickListener: TiketPetugasFragment): RecyclerView.Adapter<TiketPetugasAdapter.TicketViewHolder>() {
    inner class TicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(permintaanResponse: TiketPetugas) {
            with(itemView) {
                // get all data ticket
                var noTiket = permintaanResponse.no_tiket
                var judul = permintaanResponse.keterangan
                var tanggalInputTiket = permintaanResponse.tanggal
                var statusTiket = permintaanResponse.id_status_tiket
                var urgensi = permintaanResponse.urgensi
                var ratingTiket = permintaanResponse.rating
                var gedung = permintaanResponse.gedung_pelapor
                var lantai = permintaanResponse.lantai_pelapor
                var ruangan = permintaanResponse.ruangan_pelapor

                var tvStatusTiket: TextView = findViewById(R.id.status_tiket_petugas)
                var tvTanggalTiket: TextView = findViewById(R.id.tanggal_tiket_petugas)
                var tvBelumDinilai :TextView = findViewById(R.id.belum_dinilai)
//                var tvUrgensi : TextView = findViewById(R.id.tv_urgensi)
                var ivUrgensi : ImageView = findViewById(R.id.iv_urgensi)
                var tvLokasi : TextView = findViewById(R.id.tv_lokasi)

                //put data on item
                tvLokasi.text = "$gedung, Lantai $lantai, Ruang $ruangan"
                Service.statusTiketDisplay(statusTiket, tvStatusTiket)
                Service.urgensiDisplay(urgensi,ivUrgensi)

                val tanggalTiket = Service.datePetugas(tanggalInputTiket)

//                 checking if judul which is taken through keterangan is not bigger than equal 35
                if(judul?.length!! >= 35) {
                    judul = Service.judulSubStr(judul)
                    judul_tiket.text = "$judul ..."
                } else {
                    judul_tiket.text = judul
                }

                // setting border of ticket container based on its category
//                if(ratingTiket != null) {
//                    rvTiket.background = resources.getDrawable(R.drawable.border_gray)
//                } else {
//                    rvTiket.background = resources.getDrawable(R.drawable.border_shadow)
//                }
                // setting displaying of ticket's date is in bottom right corner if status is not 6 and rating is null
                if(statusTiket != 6 && ratingTiket == null) {
                    // displaying tanggal tiket
                    tanggal_tiket_petugas.text = tanggalTiket
                    // displaying status tiket on ticket_item
                    // hide rating bar
                    rating_bar_petugas_selesai.visibility = View.GONE
                    // hide ticket success section
                    tvBelumDinilai.visibility = View.GONE
                } else {
                    // hide tanggal tiket on bottom right corner
                    tvTanggalTiket.visibility = View.GONE
                    // setting if ticket has been rated, so display the stars not btn rate ticket
                    if(statusTiket == 6 && ratingTiket != null) {
                        ticket_petugas.visibility = View.GONE
                        ticket_petugas.layoutParams.width = 0
                        ticket_petugas.layoutParams.height = 0
                        // hide ticket success section
//                        ll_ticket_success.visibility = View.GONE
                        // displaying rating bar
                        // displaying status tiket as tanggal tiket
//                        tvStatusTiket.text = tanggalTiket.toString()
                    } else {
                        // displaying status tiket as tanggal tiket
                        tvTanggalTiket.visibility = View.VISIBLE
                        rating_bar_petugas_selesai.visibility = View.GONE

                        // displaying ticket success section
//                        ll_ticket_success.visibility = View.VISIBLE
                        // hide rating bar
//                        rating_bar.visibility = View.GONE
                    }
                }
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
            onTicketPetugasClickListener.onTicketPetugasItemClicked(position)
        }
    }

    override fun getItemCount(): Int = list.size

}