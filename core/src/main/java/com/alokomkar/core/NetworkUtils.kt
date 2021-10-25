package com.alokomkar.core

import java.io.IOException

inline fun <V : Any> retrofit2.Response<V>.toUpdateResponse(contextMessage: () -> String): Result<V> {
    return if (this.isSuccessful) {
        Result.Success(requireNotNull(this.body(), contextMessage))
    } else {
        val message = contextMessage()
        try {
            Result.Error(Exception("$message : ${errorBody()?.string()}"))
        } catch (ioException: IOException) {
            Result.Error(Exception("$message: Error reading IO response"))
        }
    }
}