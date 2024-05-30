package com.workspaceandroid.data.api.source.base

import com.workspaceandroid.data.api.ApiException
import com.workspaceandroid.data.api.RefreshTokenService
import retrofit2.HttpException

// TODO exception code 404 to file
abstract class BaseNetSource<ApiService>(
    private val apiService: ApiService,
    private val refreshTokenService: RefreshTokenService
) {

    @Throws(ApiException::class)
    protected suspend fun <ResponseModel: Any?> performRequest(
        request: suspend ApiService.() -> ResponseModel
    ): ResponseModel {
        return try {
            apiService.request()
        } catch (throwable: Exception) {
            if (throwable.isUnauthorizedException()) {
                refreshTokenService.refreshToken()
                apiService.request()
            } else {
                handleApiException(throwable)
                throw ApiException("exception here -> $throwable") //TODO 403 admin get phrases // 500 predictions
            }
        }
    }

    private fun Exception.isUnauthorizedException(): Boolean {
        return this is HttpException && this.code() == 401
    }

    private fun handleApiException(throwable: Exception) { //TODO check 404 not found user
//        if (throwable is HttpException) {
//            if (throwable.code() == 401) {
//                refreshTokenService.refreshToken()
//
//            }
//                val errorBody = throwable.response()?.errorBody()
//            }
    }

}