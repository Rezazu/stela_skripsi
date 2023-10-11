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
import kotlinx.android.synthetic.main.dokumen_item.view.*
import java.util.ArrayList
//http://10.200.49.54:8000/storage/index/tiket-image-laporan/2023-10-08%2007-53-53-6521fda1502dd/ext/pdf
//http://10.200.49.54:8000/storage/index/dokumen-lampiran/08-10-20239060255266402013267/ext/pdf

class DokumenLampiranAdapter(
    private val context: Context,
    private val listName: ArrayList<String>,
    private val listPath: ArrayList<String>,
    private val listExt: ArrayList<String>,
): RecyclerView.Adapter<DokumenLampiranAdapter.DokumenLampiranViewHolder>() {
    inner class DokumenLampiranViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dokumenLampiranName: String, dokumenLampiranPath: String, dokumenLampiranExt: String) {
            with(itemView) {
                val nameDokumen = dokumenLampiranName
                tv_dokumen_lampiran.text = nameDokumen

                val extDokumen = dokumenLampiranExt
                val BaseUrl = Retrofit.BASE_URL.dropLast(4)

                ll_ticket.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(BaseUrl + "storage/index/dokumen-lampiran/$nameDokumen/ext/$extDokumen")
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
        holder.bind(listName[position], listPath[position], listExt[position])
    }

    override fun getItemCount(): Int = listName.size

}