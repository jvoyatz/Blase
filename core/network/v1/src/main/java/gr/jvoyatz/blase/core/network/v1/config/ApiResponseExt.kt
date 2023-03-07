package gr.jvoyatz.blase.core.network.v1.config


fun <S, E> ApiResponse<S, E>.asSuccess(): ApiSuccess<S, E>?{
    if(this is ApiSuccess){
        return this
    }
    return null
}

fun <S, E> ApiResponse<S, E>.asError(): ApiError<S, E>?{
    if(this is ApiError){
        return this
    }
    return null
}

fun <S, E> ApiResponse<S, E>.asHttpError(): HttpError<S, E>?{
    if(this is HttpError){
        return this
    }
    return null
}

fun <S, E> ApiResponse<S, E>.asNetworkError(): NetworkError<S, E>?{
    if(this is NetworkError){
        return this
    }
    return null
}

fun <S, E> ApiResponse<S, E>.asUnknownError(): gr.jvoyatz.blase.core.network.v1.config.UnknownError<S, E>?{
    if(this is gr.jvoyatz.blase.core.network.v1.config.UnknownError){
        return this
    }
    return null
}

fun<S, E> ApiResponse<S, E>.isSuccess() = this is ApiSuccess
fun<S, E> ApiResponse<S, E>.isSuccessEmpty() = (this is ApiSuccess) && this.body == null
fun<S, E> ApiResponse<S, E>.isError() = this is Error

fun<S, E> ApiResponse<S, E>.getOrNull(): S? {
    return when (this) {
        is ApiSuccess -> this.body
        else -> null
    }
}

/**
 * applies the given function, if network response
 * state is Success
 */
inline fun <S, E> ApiResponse<S, E>.onSuccess(
    crossinline onResult: S.() -> Unit
): ApiResponse<S, E> {
    if (this is ApiSuccess) {
        onResult(this.body)
    }
    return this
}

///**
// * applies the given function in case of successful response,
// * however it is transforming the body into another type
// */
//inline fun <S, E, V> ApiResponse<S, E>.onSuccess(
//    mapper: (S) -> V,
//    crossinline onResult: V.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Success) {
//        onResult(mapper(this.body))
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponse<S, E>.onSuspendedSuccess(
//    crossinline onResult: suspend S.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Success) {
//        onResult(this.body)
//    }
//    return this
//}
//suspend inline fun <S, E, V> ApiResponse<S, E>.onSuspendedSuccess(
//    mapper: (S) -> V,
//    crossinline onResult: suspend V.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Success) {
//        onResult(mapper(this.body))
//    }
//    return this
//}
//

/**
 * applies the given function, if network response
 * state is Success
 */
//inline fun <S, E> ApiResponse<S, E>.onError(
//    crossinline onResult:  Error<T>.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Error) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponse<S, E>.onSuspendedError(
//    crossinline onResult: suspend Error<T>.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Error) {
//        onResult(this)
//    }
//    return this
//}
//
///**
// * applies the given function, if network response
// * state is Success
// */
//inline fun <S, E> ApiResponse<S, E>.onHttpError(
//    crossinline onResult:  Error.HttpError<T>.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Error.HttpError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponse<S, E>.onSuspendedHttpError(
//    crossinline onResult: suspend Error.HttpError<T>.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Error.HttpError) {
//        onResult(this)
//    }
//    return this
//}
//
///**
// * applies the given function, if network response
// * state is Success
// */
//inline fun <S, E> ApiResponse<S, E>.onNetworkError(
//    crossinline onResult:  Error.NetworkError<T>.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Error.NetworkError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponse<S, E>.onSuspendedNetworkError(
//    crossinline onResult: suspend Error.NetworkError<T>.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Error.NetworkError) {
//        onResult(this)
//    }
//    return this
//}
//
///**
// * applies the given function, if network response
// * state is Success
// */
//inline fun <S, E> ApiResponse<S, E>.onUnknownError(
//    crossinline onResult:  Error.UnknownError<T>.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Error.UnknownError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponse<S, E>.onSuspendedUnknownError(
//    crossinline onResult: suspend Error.UnknownError<T>.() -> Unit
//): ApiResponse<S, E> {
//    if (this is Error.UnknownError) {
//        onResult(this)
//    }
//    return this
//}
//
//inline fun<S, E> ApiResponse<S, E>.toFlow(): Flow<T>{
//    return if(this is Success){
//        flowOf(this.body)
//    }else{
//        emptyFlow()
//    }
//}
//
//inline fun <T: Any, R> ApiResponse<S, E>.toFlow(
//    crossinline transformer: T.() -> R
//): Flow<R>{
//    return if(this is Success){
//        flowOf(this.body.transformer())
//    }else{
//        emptyFlow()
//    }
//}
//suspend inline fun <T: Any, R> ApiResponse<S, E>.toSuspendFlow(
//    crossinline transformer: suspend T.() -> R
//): Flow<R>{
//    return if(this is Success){
//        flowOf(this.body.transformer())
//    }else{
//        emptyFlow()
//    }
//}
