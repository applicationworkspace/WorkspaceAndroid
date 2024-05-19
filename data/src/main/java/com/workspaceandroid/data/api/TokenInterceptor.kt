package com.workspaceandroid.data.api

import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val preferences: AppSharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (preferences.getAuthToken().isNotEmpty())
            builder.addHeader("Authorization", "Bearer " + preferences.getAuthToken())
        return chain.proceed(builder.build())
    }
}