package com.workspaceandroid.data.dto.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenNetDTO(
    @SerializedName("access_token")
    val accessToken: String
)