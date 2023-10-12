package com.example.stela_android.Retrofit.Ticket.DokumenLampiran

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.LaporanPetugas.LaporanPetugasResponse
import kotlinx.android.synthetic.main.dokumen_item.view.*
import java.util.ArrayList

class DokumenLampiranAdapter(
    private val context: Context,
    private val lampiranList: ArrayList<DokumenLampiranResponse>,
): RecyclerView.Adapter<DokumenLampiranAdapter.DokumenLampiranViewHolder>() {
    inner class DokumenLampiranViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(lampiran : DokumenLampiranResponse) {
            with(itemView) {
                val namaDokumen = lampiran.doc_name
                tv_dokumen_lampiran.text = namaDokumen
                val extDokumen = lampiran.ext
                val BaseUrl = Retrofit.BASE_URL.dropLast(4)

                rl_dokumen.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(BaseUrl + "storage/index/dokumen-lampiran/$namaDokumen/ext/$extDokumen")
                    context.startActivity(i)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DokumenLampiranViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dokumen_item, parent, false)
        return DokumenLampiranViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DokumenLampiranViewHolder,
        position: Int
    ) {
        holder.bind(lampiranList[position])
    }

    override fun getItemCount(): Int = lampiranList.size

}