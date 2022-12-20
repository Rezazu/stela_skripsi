package com.example.stela_android.Retrofit.Ticket.DokumenLampiran

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Ticket.OnTicketClickListener
import kotlinx.android.synthetic.main.activity_form.view.*
import kotlinx.android.synthetic.main.dokumen_item.*
import kotlinx.android.synthetic.main.dokumen_item.view.*
import kotlinx.android.synthetic.main.ticket_item.view.*
import java.util.ArrayList

class DokumenLampiranAdapter(private val context: Context, private val listName: ArrayList<String>, private val listPath: ArrayList<String>): RecyclerView.Adapter<DokumenLampiranAdapter.DokumenLampiranViewHolder>() {
    inner class DokumenLampiranViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dokumenLampiranName: String, dokumenLampiranPath: String) {
            with(itemView) {
                val nameDokumen = dokumenLampiranName
                tv_dokumen_lampiran.text = nameDokumen

                val pathDokumen = dokumenLampiranPath
                tv_url.text = pathDokumen

                ll_ticket.setOnClickListener {
                    Log.d("DATA", "data: " + pathDokumen)
                    val url = pathDokumen
                    val bukeBrowser = Intent(Intent.ACTION_VIEW)
                    bukeBrowser.data = Uri.parse(url)
                    startActivity(context,  bukeBrowser, null)
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
        holder.bind(listName[position], listPath[position])
    }

    override fun getItemCount(): Int = listName.size

}