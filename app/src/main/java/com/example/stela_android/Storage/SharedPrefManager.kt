package com.example.stela_android.Storage


import android.content.Context
import android.media.session.MediaSession.Token
import com.example.stela_android.Retrofit.Data
import com.example.stela_android.Retrofit.Ticket.Tiket
import com.example.stela_android.Retrofit.User

class SharedPrefManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("token", null) != null
        }

    val user: User
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getString("unit_kerja", null),
                sharedPreferences.getString("nama_lengkap", null),
                sharedPreferences.getString("telepon", null),
                sharedPreferences.getString("hp", null),
                sharedPreferences.getString("gedung", null),
                sharedPreferences.getString("bagian", null),
                sharedPreferences.getString("ruangan", null),
                sharedPreferences.getStringSet("peran", null),
                sharedPreferences.getString("lantai", null),
                sharedPreferences.getString("kd_departemen", null),
                sharedPreferences.getInt("id", 0),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("status", null)
            )
        }

    val tickets: Tiket
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return Tiket(
                sharedPreferences.getInt("id", 0),
                sharedPreferences.getString("no_tiket", null),
                sharedPreferences.getInt("id_sub_kategori", 0),
                sharedPreferences.getInt("id_status_tiket", 0),
                sharedPreferences.getInt("id_via", 0),
                sharedPreferences.getInt("id_tim_programmer", 0),
                sharedPreferences.getInt("id_aplikasi", 0),
                sharedPreferences.getInt("id_pelapor", 0),
                sharedPreferences.getInt("id_urgensi", 0),
                sharedPreferences.getInt("id_status_tiket_internal", 0),
                sharedPreferences.getString("nama_pelapor", null),
                sharedPreferences.getString("bagian_pelapor", null),
                sharedPreferences.getString("gedung_pelapor", null),
                sharedPreferences.getString("unit_kerja_pelapor", null),
                sharedPreferences.getString("ruang_pelapor", null),
                sharedPreferences.getString("lantai_pelapor", null),
                sharedPreferences.getString("telepon_pelapor", null),
                sharedPreferences.getString("hp_pelapor", null),
                sharedPreferences.getString("email_pelapor", null),
                sharedPreferences.getString("keterangan", null),
                sharedPreferences.getString("permasalahan_akhir", null),
                sharedPreferences.getString("solusi", null),
                sharedPreferences.getString("tanggal_pelaksanaan", null),
                sharedPreferences.getInt("rating", 0),
                sharedPreferences.getInt("status", 0),
                sharedPreferences.getString("status_revisi", null),
                sharedPreferences.getString("keterangan_revisi", null),
                sharedPreferences.getString("tanggal_input", null),
            )
        }

    fun saveUser(user: User) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("unit_kerja", user.unitKerja)
        editor.putString("nama_lengkap", user.namaLengkap)
        editor.putString("telepon", user.telepon)
        editor.putString("hp", user.hp)
        editor.putString("gedung", user.gedung)
        editor.putString("bagian", user.bagian)
        editor.putString("ruangan", user.ruangan)
        editor.putStringSet("peran", user.peran)
        editor.putString("lantai", user.lantai)
        editor.putString("kd_departemen", user.kdDepartemen)
        editor.putInt("id", user.id)
        editor.putString("email", user.email)
        editor.putString("username", user.username)
        editor.putString("status", user.status)
        editor.apply()

    }

    fun saveToken(token: String){
        val prefs = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putString("token", token)
        editor.apply()
    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"

        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}