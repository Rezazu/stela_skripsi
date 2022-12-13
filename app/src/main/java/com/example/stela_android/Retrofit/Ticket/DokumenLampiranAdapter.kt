package com.example.stela_android.Retrofit.Ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R

class DokumenLampiranAdapter(private val list: ArrayList<DokumenLampiranResponse>): RecyclerView.Adapter<DokumenLampiranAdapter.DokumenLampiranViewHolder>() {

    inner class DokumenLampiranViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dokumenLampiranResponse: DokumenLampiranResponse) {
            with(itemView) {
                val id_dokumen = dokumenLampiranResponse.id_dokumen
                val original_name = dokumenLampiranResponse.original_name
                val path = dokumenLampiranResponse.path
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DokumenLampiranAdapter.DokumenLampiranViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dokumen_item, parent, false)
        return DokumenLampiranViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DokumenLampiranAdapter.DokumenLampiranViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}