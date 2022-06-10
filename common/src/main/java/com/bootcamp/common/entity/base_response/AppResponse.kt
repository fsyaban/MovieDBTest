package com.bootcamp.common.entity.base_response

import okhttp3.ResponseBody

sealed class AppResponse<T>(
    val data: T?, val error: Exception?, val code: Int?,
    val errorBody: ResponseBody?
) {
    companion object {
        fun <T> success(t: T) : AppResponse<T> = ResponseSuccess(t)
        fun <T> errorSystem(exc: Exception) : AppResponse<T> =
            ResponseError(exc, ResponseError.ERROR_SYSTEM, null)

        fun <T> errorBackend(statusCode: Int, body: ResponseBody?): AppResponse<T> =
            ResponseError(null, statusCode, body)

        fun <T> loading(): AppResponse<T> = ResponseLoading()

    }
}

class ResponseSuccess<T>(data: T) : AppResponse<T>(data, null, null, null)

class ResponseError<T>(exc: Exception?, code: Int, responseBody: ResponseBody?): AppResponse<T>(
    null, exc, code, responseBody
){
    companion object{
        const val ERROR_SYSTEM = -1
    }
}

class ResponseLoading<T> : AppResponse<T>(null, null, null, null)