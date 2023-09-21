package com.example.stela_android.Service

import android.content.ContentResolver
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.widget.RelativeLayout
import android.widget.TextView
import android.view.View
import android.widget.ImageView
import com.example.stela_android.R
import kotlinx.android.synthetic.main.ticket_item.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

object Service {

    fun date(date: String?): String {
        val m = date?.subSequence(5,7)
        val d = date?.subSequence(8,10)
        val y = date?.subSequence(0,4)
        val time = date?.subSequence(11,16)

        var monthName: String? = ""

        when(m) {
            "01" -> monthName = "Jan"
            "02" -> monthName = "Feb"
            "03" -> monthName = "Mar"
            "04" -> monthName = "Apr"
            "05" -> monthName = "Mei"
            "06" -> monthName = "Jun"
            "07" -> monthName = "Jul"
            "08" -> monthName = "Agu"
            "09" -> monthName = "Sep"
            "10" -> monthName = "Okt"
            "11" -> monthName = "Nov"
            "12" -> monthName = "Des"
        }

        return "$d $monthName $y, $time"

    }

    fun judulSubStr(judul: String?): String? {
        val newJudul = judul?.subSequence(0, 30) as String?
        return newJudul

    }

    fun urgensiDisplay(urgensi: String?, circle: ImageView, tvUrgensi: TextView){
        if (urgensi == "Critical") {
            circle.setColorFilter(Color.parseColor("#FF0000"))
            tvUrgensi.text="Critical"
        } else if (urgensi == "High") {
            circle.setColorFilter(Color.parseColor("#FF731D"))
            tvUrgensi.text="High"
        } else if (urgensi == "Normal") {
            circle.setColorFilter(Color.parseColor("#FFE600"))
            tvUrgensi.text="Normal"
        } else if (urgensi == "Low") {
            circle.setColorFilter(Color.parseColor("#43A241"))
            tvUrgensi.text="Low"
        }
    }

    fun statusTiketDisplay(statusTiket: Int?, tv: TextView) {
        if(statusTiket == 1) {
            tv.text = "Open"
            tv.setTextColor(Color.parseColor("#858383"))
        } else if(statusTiket == 2) {
            tv.text = "On Hold"
            tv.setTextColor(Color.parseColor("#E21C1C"))
        } else if(statusTiket == 3) {
            tv.text = "On Hold(Item Not Available)"
            tv.setTextColor(Color.parseColor("#E21C1C"))
        } else if(statusTiket == 4) {
            tv.text = "Pending"
            tv.setTextColor(Color.parseColor("#FDD736"))
        } else if(statusTiket == 5) {
            tv.text = "OnProcess"
            tv.setTextColor(Color.parseColor("#5F9DF7"))
        } else if(statusTiket == 6) {
            tv.text = "Done"
            tv.setTextColor(Color.parseColor("#47BB8A"))
        } else if(statusTiket == 7) {
            tv.text = "Waiting"
            tv.setTextColor(Color.parseColor("#FDD736"))
        } else if(statusTiket == 8) {
            tv.text = "Assigned"
            tv.setTextColor(Color.parseColor("#2954A8"))
        } else if(statusTiket == 9) {
            tv.text = "Solved"
            tv.setTextColor(Color.parseColor("#47BB8A"))
        }
    }


}