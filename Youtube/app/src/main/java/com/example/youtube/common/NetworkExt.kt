package com.example.youtube.common

import com.example.youtube.model.sealed.Results

suspend fun <T : Any> safeApiCall(call: suspend () -> Results<T>): Results<T> {
    return try {
        call()
    } catch (e: Exception) {
        Results.Error(e.localizedMessage, e)
    }
}
