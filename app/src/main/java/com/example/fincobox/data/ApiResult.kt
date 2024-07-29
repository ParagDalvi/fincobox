package com.example.fincobox.data

import com.example.fincobox.presentation.UiState

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: ApiException) : ApiResult<Nothing>()
}

sealed class ApiException : Exception() {
    data class Network(override val message: String? = null, val code: Int? = null) : ApiException()
    data class Server(override val message: String? = null, val code: Int? = null) : ApiException()
    data class Client(override val message: String? = null, val code: Int? = null) : ApiException()
    data class Unknown(override val message: String? = null, val code: Int? = null) : ApiException()
}

fun <T, R> ApiResult<T>.toUiStates(mapper: ((T) -> R)): UiState<R> {
    return when (this) {
        is ApiResult.Success -> UiState.Success(mapper(data))
        is ApiResult.Error -> UiState.Error(exception.message)
    }
}
