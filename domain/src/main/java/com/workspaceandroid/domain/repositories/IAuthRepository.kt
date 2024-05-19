package com.workspaceandroid.domain.repositories

import com.workspaceandroid.domain.models.auth.UserDetails

interface IAuthRepository {
    suspend fun login(email: String, password: String): UserDetails
    suspend fun signUp(email: String, password: String)
}