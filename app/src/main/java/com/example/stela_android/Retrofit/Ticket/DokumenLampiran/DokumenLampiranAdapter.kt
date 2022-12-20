package com.example.stela_android.Retrofit.Ticket.DokumenLampiran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import kotlinx.android.synthetic.main.dokumen_item.view.*
import java.util.ArrayList

class DokumenLampiranAdapter(private val list: ArrayList<DokumenLampiranResponse>): RecyclerView.Adapter<DokumenLampiranAdapter.DokumenLampiranViewHolder>() {
    inner class DokumenLampiranViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dokumenLampiranResponse: DokumenLampiranResponse) {
            with(itemView) {
                val id_dokumen = dokumenLampiranResponse.id_dokumen
                val original_name = dokumenLampiranResponse.original_name
                val path = dokumenLampiranResponse.path

                tv_dokumen_name.text = original_name
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
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}