package com.example.stela_android.Retrofit.Ticket.LaporanPetugas

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Ticket.OnTicketClickListener
import kotlinx.android.synthetic.main.activity_form.view.*
import kotlinx.android.synthetic.main.dokumen_item.*
import kotlinx.android.synthetic.main.dokumen_item.view.*
import kotlinx.android.synthetic.main.ticket_item.view.*
import java.util.ArrayList

class LaporanPetugasAdapter(private val context: Context, private val listName: ArrayList<String>, private val listPath: ArrayList<String>): RecyclerView.Adapter<LaporanPetugasAdapter.LaporanPetugasViewHolder>() {
    inner class LaporanPetugasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(LaporanPetugasName: String, LaporanPetugasPath: String) {
            with(itemView) {
                val nameDokumen = LaporanPetugasName
                tv_dokumen_lampiran.text = nameDokumen

                val pathDokumen = LaporanPetugasPath
                tv_url.text = pathDokumen

                ll_ticket.setOnClickListener {
                    Log.d("DATA", "data: " + pathDokumen)
//                    val url = pathDokumen
//                    val bukeBrowser = Intent(Intent.ACTION_VIEW)
//                    bukeBrowser.data = Uri.parse(url)
//                    startActivity(context,  bukeBrowser, null)

                    val uri: Uri = Uri.parse(Environment.getExternalStorageDirectory().toString()+nameDokumen)
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, "*/*")
                    startActivity(context,Intent.createChooser(intent, "Open folder"),null)
//                    startActivity(Intent.createChooser(intent, "Open folder"))
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
        holder.bind(listName[position], listPath[position])
    }

    override fun getItemCount(): Int = listName.size

}