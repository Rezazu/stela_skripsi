package com.example.stela_android.Stela

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stela_android.Form.FormActivity
import com.example.stela_android.Homepage.Home
import com.example.stela_android.R
import com.example.stela_android.Ticket.ActiveTicketPage
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_notifications_page.*
import kotlinx.android.synthetic.main.fragment_stela_page.*
import kotlinx.android.synthetic.main.fragment_stela_page.back_btn
import java.io.File


class StelaPage: Fragment() {
    lateinit var pdfView: PDFView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stela_page, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pdfView()
        btnListener()
    }
    fun pdfView(){
        pdfView = requireView().findViewById(R.id.pdfView)
        pdfView.fromAsset("Panduan_Penggunaan_stela.pdf")
            .pages(0)
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .defaultPage(0)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(0)
            .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
            .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
            .pageSnap(false) // snap pages to screen boundaries
            .pageFling(false) // make a fling change only a single page like ViewPager
            .nightMode(false) // toggle night mode
            .load()
    }

    private fun btnListener() {
        val activeTicket = ActiveTicketPage()
        val home = Home()
        btn_aktif.setOnClickListener{
            setCurrentFragment(activeTicket)
        }
        btn_baru.setOnClickListener{
            startActivity(Intent(activity, FormActivity::class.java))
        }
        btn_bacapdf.setOnClickListener{
            startActivity(Intent(activity, com.example.stela_android.Stela.PDFView::class.java))
        }
        back_btn.setOnClickListener{
            setCurrentFragment(home)
        }
    }

    fun setCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.commit()
    }
}