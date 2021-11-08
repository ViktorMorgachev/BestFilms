package com.pet.bestfilms.data

import java.util.concurrent.CancellationException
import kotlin.Exception

sealed class Result<out S, out E> {
    data class Success<out S>(val result: S) : Result<S, Nothing>()
    data class Error<out E>(val errorInfo: E) : Result<Nothing, E>()
}

inline fun <reified R, reified T> R.tryGetResult(block: R.()->T): Result<T, Exception>{
    return try {
        Result.Success(block())
    } catch (e: CancellationException){
        throw e
    } catch (e: Exception){
        Result.Error(e)
    }
}

inline fun <S, E> Result<out S, out E>.doOnError(block: (E) -> Unit): Result<S, E>{
    if (this is Result.Error){
        block(this.errorInfo)
    }
    return this
}

inline fun <S, E> Result<out S, out E>.doOnSuccess(block: (S) -> Unit): Result<S, E>{
    if (this is Result.Success){
        block(this.result)
    }
    return this
}

inline fun <S, E, T> Result<S, T>.mapOnSuccess(block: (S) -> E): Result<E, T> {
   return when (this) {
        is Result.Success -> Result.Success(result = block(this.result))
        is Result.Error -> Result.Error(errorInfo = this.errorInfo)
    }
}