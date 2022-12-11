package com.example.stela_android.Storage

import android.content.Context
import android.media.session.MediaSession.Token
import com.example.stela_android.Storage.Constants.PREFS_TOKEN_FILE
import com.example.stela_android.Storage.Constants.USER_TOKEN


class TokenManager  (context: Context) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken() :String? {
        return prefs.getString(USER_TOKEN, null)

    }
}