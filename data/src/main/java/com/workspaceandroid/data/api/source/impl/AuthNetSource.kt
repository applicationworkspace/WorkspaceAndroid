package com.workspaceandroid.data.api.source.impl

import com.workspaceandroid.data.api.RefreshTokenService
import com.workspaceandroid.data.api.service.AuthApiService
import com.workspaceandroid.data.api.source.IAuthNetSource
import com.workspaceandroid.data.api.source.base.BaseNetSource
import com.workspaceandroid.data.dto.auth.AuthResponseNetDTO
import com.workspaceandroid.data.dto.auth.AuthorizationNetDTO

internal class AuthNetSource(
    authApiService: AuthApiService,
    refreshTokenService: RefreshTokenService
): BaseNetSource<AuthApiService>(authApiService, refreshTokenService), IAuthNetSource {

    override suspend fun auth(email: String, password: String): AuthResponseNetDTO {
        return performRequest {
            auth(AuthorizationNetDTO(email, password))
        }
    }

}