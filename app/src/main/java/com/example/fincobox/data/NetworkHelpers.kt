package com.example.fincobox.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> apiCall(apiCall: suspend () -> T): ApiResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ApiResult.Error(ApiException.Network(throwable.message))
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = throwable.response()?.errorBody()?.string()
                    if (code in 400..499) {
                        ApiResult.Error(ApiException.Client(errorResponse, code))
                    } else {
                        ApiResult.Error(ApiException.Server(errorResponse, code))
                    }
                }

                else -> ApiResult.Error(ApiException.Unknown(throwable.message))
            }
        }
    }
}