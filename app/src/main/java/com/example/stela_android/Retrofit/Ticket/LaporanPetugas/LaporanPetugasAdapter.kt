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


class LaporanPetugasAdapter(
    private val Activity: Context,
    private val listName: ArrayList<String>,
    private val listPath: ArrayList<String>,
    private val listExt: ArrayList<String>,
): RecyclerView.Adapter<LaporanPetugasAdapter.LaporanPetugasViewHolder>() {
    inner class LaporanPetugasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(LaporanPetugasName: String, LaporanPetugasPath: String, LaporanPetugasExt: String) {
            with(itemView) {
                var nameDokumen = LaporanPetugasName
                tv_dokumen_lampiran.text = nameDokumen
                val pathDokumen = LaporanPetugasPath
                val extDokumen = LaporanPetugasExt
                val BaseUrl = Retrofit.BASE_URL.dropLast(4)

                ll_ticket.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(BaseUrl + "storage/index/tiket-image-laporan/$nameDokumen/ext/$extDokumen")
                    context.startActivity(i)

//                  http://10.200.177.211:8000/storage/index/tiket-image-laporan/2023-10-11%2001-24-28-65263f9cabae7/ext/pdf
//                  http://10.200.177.211:8000/storage/index/tiket-image-laporan/2023-10-11%2001-24-28-65263f9cabae7/ext/pdf
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
        holder.bind(listName[position],listPath[position],listExt[position])
    }

    override fun getItemCount(): Int = listName.size

}