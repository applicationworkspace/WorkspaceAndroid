package com.workspaceandroid.data.repositories

import com.workspaceandroid.data.api.source.IAuthNetSource
import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import com.workspaceandroid.data.database.source.ProfileDbSource
import com.workspaceandroid.data.mappers.UserDetailsNetMapper
import com.workspaceandroid.domain.models.auth.UserDetails
import com.workspaceandroid.domain.repositories.IAuthRepository

class AuthRepository(
    private val netSource: IAuthNetSource,
    private val dbSource: ProfileDbSource,
    private val sharedPreferencesSource: AppSharedPreferences,
    private val userDetailsNetMapper: UserDetailsNetMapper
): IAuthRepository {

    override suspend fun login(email: String, password: String): UserDetails {
        val authResponse = netSource.auth(email, password)
        return if (authResponse.user != null) {
            sharedPreferencesSource.setAuthToken(authResponse.token)
            authResponse.user.run(userDetailsNetMapper::mapFromEntity)
        } else UserDetails(-1, "", "")
    }

    override suspend fun signUp(email: String, password: String) {
        TODO("Not yet implemented")
    }
}