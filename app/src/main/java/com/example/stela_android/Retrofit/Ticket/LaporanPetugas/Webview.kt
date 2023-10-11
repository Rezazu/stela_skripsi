package com.example.stela_android.Retrofit.Ticket.LaporanPetugas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.stela_android.R
import kotlinx.android.synthetic.main.activity_webview.*

class Webview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val myWebView: WebView = findViewById(R.id.webview)
        val url = intent.getStringExtra("pathDokumen").toString()
        path.text = url
        myWebView.loadUrl(url)

//        /storage/index/tiket-image-laporan/{$data['doc_name']}/ext/{$data['ext']}'
    }
}