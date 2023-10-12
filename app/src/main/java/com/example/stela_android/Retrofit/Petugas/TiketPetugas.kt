package com.example.stela_android.Retrofit.Petugas

import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranResponse
import com.example.stela_android.Retrofit.Ticket.LaporanPetugas.LaporanPetugasResponse
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class TiketPetugas(

    @field:SerializedName("id_tiket")
    val id_tiket: Int,

    @field:SerializedName("no_tiket")
    val no_tiket: String? = null,

    @field:SerializedName("pelapor")
    val nama_pelapor: String? = null,

    @field:SerializedName("bagian")
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

    @field:SerializedName("tanggal_input")
    val tanggal_input: String? = null,

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

    @field:SerializedName("keterangan_rating")
    val keterangan_rating: String? = null,

    @field:SerializedName("permasalahan_akhir")
    val permasalahan_akhir: String? = null,

    @field:SerializedName("solusi")
    val solusi: String? = null,

    @field:SerializedName("dokumen_lampiran")
    val dokumen_lampiran: ArrayList<DokumenLampiranResponse>? = null,

    @field:SerializedName("laporan_petugas")
    val laporan_petugas: ArrayList<LaporanPetugasResponse>? = null,

    @field:SerializedName("urgensi")
    val urgensi:String? = null

    )

