package com.example.stela_android.Stela

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stela_android.R
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle

class PDFView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)

        lateinit var pdfView: com.github.barteksc.pdfviewer.PDFView
        pdfView = findViewById(R.id.pdfView)
        pdfView.fromAsset("Panduan_Penggunaan_stela.pdf")
            .scrollHandle(DefaultScrollHandle(this))
            .load()
    }
}