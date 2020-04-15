package com.example.youtube.common.extensions

import com.example.youtube.common.helper.Results

suspend fun <T : Any> safeApiCall(call: suspend () -> Results<T>): Results<T> {
    return try {
        call()
    } catch (e: Exception) {
        Results.Error(e.localizedMessage, e)
    }
}
