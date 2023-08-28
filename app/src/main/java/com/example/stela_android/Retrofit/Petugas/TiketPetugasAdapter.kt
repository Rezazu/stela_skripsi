package com.example.stela_android.Retrofit.Petugas

import android.content.Context
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
                var id = permintaanResponse.id_tiket
                var noTiket = permintaanResponse.no_tiket
                var judul = permintaanResponse.keterangan
                var tanggalInputTiket = permintaanResponse.tanggal
                var kategori = permintaanResponse.id_sub_kategori
                var statusTiket = permintaanResponse.id_status_tiket
                var urgensi = permintaanResponse.id_urgensi
                var ratingTiket = permintaanResponse.rating

                // get container of item
                val rvTiket: RelativeLayout = findViewById(R.id.ticket_petugas)
                // get tv status tiket
                var tvStatusTiket: TextView = findViewById(R.id.status_tiket_petugas)
                // get tv kode tiket
                var tvKodeTiket: TextView = findViewById(R.id.kode_tiket)
                // get tv tanggal tiket
                var tvTanggalTiket: TextView = findViewById(R.id.tanggal_tiket_petugas)

                //put data on item
                tvKodeTiket.text = noTiket.toString()
                tvTanggalTiket.text = tanggalInputTiket.toString()
                Service.statusTiketDisplay(statusTiket, tvStatusTiket)

//                 checking if judul which is taken through keterangan is not bigger than equal 35
                if(judul?.length!! >= 35) {
                    judul = Service.judulSubStr(judul)
                    judul_tiket.text = "$judul ..."
                } else {
                    judul_tiket.text = judul
                }
                // setting timestamp retrieved to the format asked
                val tanggalTiket = Service.date(tanggalInputTiket)

                // setting border of ticket container based on its category
//                if(ratingTiket != null) {
//                    rvTiket.background = resources.getDrawable(R.drawable.border_gray)
//                } else {
//                    rvTiket.background = resources.getDrawable(R.drawable.border_shadow)
//                }
                // setting displaying of ticket's date is in bottom right corner if status is not 6 and rating is null
                if(statusTiket != 6 && ratingTiket == null) {
                    // displaying tanggal tiket
                    // displaying status tiket on ticket_item
                    // hide rating bar
//                    rating_bar.visibility = View.GONE
                    // hide ticket success section
//                    ll_ticket_success.visibility = View.GONE
                } else {
                    // hide tanggal tiket on bottom right cornere
//                    tanggal_tiket.visibility = View.GONE
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
                        tvStatusTiket.text = tanggalTiket.toString()
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
//        holder.itemView.btn_nilai.setOnClickListener {
//            val bundle = Bundle()
//            DialogRate(context, list[position]?.id).show()
//        }
    }

    override fun getItemCount(): Int = list.size

}