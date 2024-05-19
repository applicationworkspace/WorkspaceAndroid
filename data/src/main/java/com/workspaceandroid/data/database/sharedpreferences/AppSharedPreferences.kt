package com.workspaceandroid.data.database.sharedpreferences

interface AppSharedPreferences {
    fun setAuthToken(key: String?)
    fun getAuthToken(): String
}