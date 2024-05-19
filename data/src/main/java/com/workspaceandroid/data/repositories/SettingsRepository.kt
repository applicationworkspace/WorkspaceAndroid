package com.workspaceandroid.data.repositories

import com.workspaceandroid.data.api.source.IAuthNetSource
import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import com.workspaceandroid.data.database.source.ProfileDbSource
import com.workspaceandroid.data.mappers.UserDetailsNetMapper
import com.workspaceandroid.domain.models.auth.UserDetails
import com.workspaceandroid.domain.repositories.IAuthRepository
import com.workspaceandroid.domain.repositories.ISettingsRepository

class SettingsRepository(
    private val netSource: IAuthNetSource,
    private val dbSource: ProfileDbSource,
    private val sharedPreferencesSource: AppSharedPreferences,
    private val userDetailsNetMapper: UserDetailsNetMapper
): ISettingsRepository {

    override suspend fun getUserDetails(): UserDetails {
        TODO("Not yet implemented")
    }
}