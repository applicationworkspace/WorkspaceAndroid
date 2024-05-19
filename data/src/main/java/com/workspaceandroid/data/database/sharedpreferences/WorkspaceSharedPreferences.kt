package com.workspaceandroid.data.database.sharedpreferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Singleton

@Singleton
class WorkspaceSharedPreferences(context: Context): AppSharedPreferences {

    companion object {
        const val KEY_AUTH_KEY = "auth_key"
        const val SHARED_PREFERENCES_NAME = "sp_workspace"
    }

    private val sharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            SHARED_PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
    }
//    context.getSharedPreferences(
//            SHARED_PREFERENCES_NAME,
//            Context.MODE_PRIVATE
//        )

    override fun setAuthToken(key: String?) {
        if (key != null) {
            sharedPreferences.edit().putString(KEY_AUTH_KEY, key).apply()
        } else {
            sharedPreferences.edit().putString(KEY_AUTH_KEY, "").apply()
        }
    }

    override fun getAuthToken(): String = sharedPreferences.getString(KEY_AUTH_KEY, "") ?: ""
}