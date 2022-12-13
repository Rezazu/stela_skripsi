package com.example.stela_android.Retrofit

import com.example.stela_android.Storage.SharedPrefManager
import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor constructor(
    private val tokenType:String,
    private val JWToken:String
) :Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $JWToken")
            .build()
        return chain.proceed(request)
    }
}