package gr.jvoyatz.core.common

import gr.jvoyatz.core.common.ResultWrapper.Loading
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class ResultWrapper<out T>{
    data class Success<T>(val data: T): ResultWrapper<T>()

    data class Error(val exception: Throwable? = null): ResultWrapper<Nothing>()

    object Loading : ResultWrapper<Nothing>()

    companion object{
        fun <T> success(data: T): Success<T> = Success(data)

        fun error(exception: Throwable): Error = Error(exception)

        fun loading() = Loading
    }
}

fun <T> ResultWrapper<T>.isSuccess(): Boolean = this is ResultWrapper.Success

fun <T> ResultWrapper<T>.isError(): Boolean = this is ResultWrapper.Error

fun <T> ResultWrapper<T>.asSuccess(): ResultWrapper.Success<T>? {
    return if (this is ResultWrapper.Success){
        return this
    }else{
        null
    }
}

fun <T> ResultWrapper<T>.asError(): ResultWrapper.Error? {
    return if (this is ResultWrapper.Error){
        return this
    }else{
        null
    }
}

inline fun <T> ResultWrapper<T>.onSuccess(action: (value: T) -> Unit): ResultWrapper<T> {
    if(isSuccess()) action(asSuccess()!!.data)
    return this
}

inline fun <T> ResultWrapper<T>.onError(action: (value: Throwable) -> Unit): ResultWrapper<T> {
    if(isError()) asError()!!.exception?.let{
        action(it)
    }
    return this
}


fun <T> Flow<T>.asResult(): Flow<ResultWrapper<T>> {
    return this
    .onStart { ResultWrapper.loading() }
    .map<T, ResultWrapper<T>> {
        ResultWrapper.success(it)
    }.onStart {
        emit(Loading)
    }.catch {
        emit(ResultWrapper.error(it))
    }
}


/**
 * Like [runCatching], but with proper coroutines cancellation handling.
 * Also only catches [Exception] instead of [Throwable].
 *
 * Cancellation exceptions need to be rethrown. See https://github.com/Kotlin/kotlinx.coroutines/issues/1814.
 */
inline fun <R> resultOf(block: () -> R): ResultWrapper<R> {
    return try {
        ResultWrapper.success(block())
    } catch (t: TimeoutCancellationException) {
        ResultWrapper.error(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Exception) {
        ResultWrapper.error(e)
    }
}
