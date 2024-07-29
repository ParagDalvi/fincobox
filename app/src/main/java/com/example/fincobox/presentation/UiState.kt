package com.example.fincobox.presentation

sealed class UiState<T> {
    class Loading<T> : UiState<T>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error<T>(val data: Any? = null) : UiState<T>()
}