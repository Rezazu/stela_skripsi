package com.example.stela_android.Storage


import android.content.Context
import android.media.session.MediaSession.Token
import com.example.stela_android.Retrofit.Data
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
                sharedPreferences.getString("unitKerja", null),
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
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("status", null)
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