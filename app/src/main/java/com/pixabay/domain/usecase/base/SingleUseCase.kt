package com.pixabay.domain.usecase.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class SingleUseCase<T> {

    internal abstract suspend fun buildUseCaseSingle(): T

    suspend fun execute(
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable?) -> Unit),
    ) {
        withContext(Dispatchers.IO){
            try {
                onSuccess(buildUseCaseSingle())
            }catch (exception : Exception){
                onError(exception)
            }
        }
    }
}