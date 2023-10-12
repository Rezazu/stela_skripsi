package com.example.stela_android.Retrofit.Ticket.LaporanPetugas

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import kotlinx.android.synthetic.main.dokumen_item.view.*
import kotlinx.android.synthetic.main.ticket_petugas_item.view.*


class LaporanPetugasAdapter(
    private val Activity: Context,
    private val laporanList: ArrayList<LaporanPetugasResponse>,
): RecyclerView.Adapter<LaporanPetugasAdapter.LaporanPetugasViewHolder>() {
    inner class LaporanPetugasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(laporan : LaporanPetugasResponse) {
            with(itemView) {
                val namaLaporan = laporan.doc_name
                val pathLaporan = laporan.path
                val extLaporan = laporan.ext
                val BaseUrl = Retrofit.BASE_URL.dropLast(4)
                tv_dokumen_lampiran.text = namaLaporan

                rl_dokumen.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(BaseUrl + "storage/index/tiket-image-laporan/$namaLaporan/ext/$extLaporan")
                    context.startActivity(i)
                }
                if (namaLaporan.isEmpty()) {
                    rl_dokumen.visibility = View.GONE
                    rl_dokumen.layoutParams.width = 0
                    rl_dokumen.layoutParams.height = 0
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LaporanPetugasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dokumen_item, parent, false)
        return LaporanPetugasViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: LaporanPetugasViewHolder,
        position: Int
    ) {
        holder.bind(laporanList[position])
    }

    override fun getItemCount(): Int = laporanList.size

}