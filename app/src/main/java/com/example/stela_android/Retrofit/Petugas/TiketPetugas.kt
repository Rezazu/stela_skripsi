package com.example.stela_android.Retrofit.Petugas

import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranResponse
import com.google.gson.annotations.SerializedName
import java.util.*

data class TiketPetugas(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("no_tiket")
    val no_tiket: String? = null,

    @field:SerializedName("nama_pelapor")
    val nama_pelapor: String? = null,

    @field:SerializedName("bagian_pelapor")
    val bagian_pelapor: String? = null,


    @field:SerializedName("gedung_pelapor")
    val gedung_pelapor: String? = null,

    @field:SerializedName("unit_kerja_pelapor")
    val unit_kerja_pelapor: String? = null,

    @field:SerializedName("ruangan_pelapor")
    val ruangan_pelapor: String? = null,

    @field:SerializedName("lantai_pelapor")
    val lantai_pelapor: String? = null,

    @field:SerializedName("hp_pelapor")
    val hp_pelapor: String? = null,

    @field:SerializedName("email_pelapor")
    val email_pelapor: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("id_sub_kategori")
    val id_sub_kategori: String? = null,

    @field:SerializedName("id_status_tiket")
    val id_status_tiket: Int? = null,

    @field:SerializedName("id_urgensi")
    val id_urgensi: Int? = null,

    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("permasalahan_akhir")
    val permasalahan_akhir: String? = null,

    @field:SerializedName("solusi")
    val solusi: String? = null,

    @field:SerializedName("dokumen_lampiran")
    val dokumen_lampiran: ArrayList<DokumenLampiranResponse>? = null,

    )

