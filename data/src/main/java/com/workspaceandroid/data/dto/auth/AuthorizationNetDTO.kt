package com.workspaceandroid.data.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthorizationNetDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)