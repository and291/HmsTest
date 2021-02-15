package ru.cardsmobile.hmstest.extentions

public inline fun <R> Result<R>.mapFailure(transform: (exception: Throwable) -> Throwable): Result<R> {
    return when (val exception = exceptionOrNull()) {
        null -> this
        else -> Result.failure(transform(exception))
    }
}