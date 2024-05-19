package com.workspaceandroid.data.api

data class ApiException(
    val api_message: String
): RuntimeException()