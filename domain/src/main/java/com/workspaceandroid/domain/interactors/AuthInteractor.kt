package com.workspaceandroid.domain.interactors

import com.workspaceandroid.domain.models.auth.UserDetails
import com.workspaceandroid.domain.repositories.IAuthRepository
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: IAuthRepository
) {

    suspend fun performAuth(email: String, password: String): UserDetails {
        return authRepository.login(email, password)
    }

    suspend fun performSignUp(email: String, password: String): UserDetails {
        return authRepository.login(email, password)
    }
}