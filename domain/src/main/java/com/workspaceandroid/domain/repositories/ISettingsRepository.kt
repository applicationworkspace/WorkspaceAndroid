package com.workspaceandroid.domain.repositories

import com.workspaceandroid.domain.models.auth.UserDetails

interface ISettingsRepository {
    suspend fun getUserDetails(): UserDetails
}