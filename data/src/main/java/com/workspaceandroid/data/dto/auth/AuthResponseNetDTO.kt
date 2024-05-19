package com.workspaceandroid.data.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthResponseNetDTO(
    @SerializedName("accessToken")
    val token: String?,
    @SerializedName("user")
    val user: UserDetailsNetDTO?
)