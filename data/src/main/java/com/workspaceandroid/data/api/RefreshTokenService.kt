package com.workspaceandroid.data.api

import android.util.Log
import com.workspaceandroid.data.api.service.AuthApiService
import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import javax.inject.Inject

class RefreshTokenService @Inject constructor(
    private val sharedPreferences: AppSharedPreferences,
    private val authApiService: AuthApiService
) {

    suspend fun refreshToken() {
        sharedPreferences.getAuthToken()
        val newToken = authApiService.refreshToken()
        Log.d("REFRESH_TOKEN", "refreshToken: $newToken")
    }

}