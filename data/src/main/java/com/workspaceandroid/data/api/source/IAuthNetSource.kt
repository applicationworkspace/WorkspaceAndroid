package com.workspaceandroid.data.api.source

import com.workspaceandroid.data.dto.auth.AuthResponseNetDTO

interface IAuthNetSource {
    suspend fun auth(email: String, password: String): AuthResponseNetDTO
}