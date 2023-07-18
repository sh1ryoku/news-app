package ru.shiryoku.news.domain.models

sealed class RequestResult<out T> {
    data class Success<out T>(val data: T) : RequestResult<T>()
    data class Error(val exception: Throwable) : RequestResult<Nothing>()
}