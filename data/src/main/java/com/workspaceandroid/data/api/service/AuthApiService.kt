package com.workspaceandroid.data.api.service

import com.workspaceandroid.data.dto.auth.AuthResponseNetDTO
import com.workspaceandroid.data.dto.auth.AuthorizationNetDTO
import com.workspaceandroid.data.dto.auth.RefreshTokenNetDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/signin")
    suspend fun auth(@Body model: AuthorizationNetDTO): AuthResponseNetDTO

    @POST("auth/refresh")
    suspend fun refreshToken(): RefreshTokenNetDTO

}