package gr.jvoyatz.blase.core.network.v1


fun <S, E> ApiResponseV2<S, E>.asSuccess():ApiResponseV2.Success<S, E>?{
    if(this is ApiResponseV2.Success){
        return this
    }
    return null
}

fun <S, E> ApiResponseV2<S, E>.asError(): ApiResponseV2.Error<S, E>?{
    if(this is ApiResponseV2.Error){
        return this
    }
    return null
}

fun <S, E> ApiResponseV2<S, E>.asHttpError(): ApiResponseV2.HttpError<S, E>?{
    if(this is ApiResponseV2.HttpError){
        return this
    }
    return null
}

fun <S, E> ApiResponseV2<S, E>.asNetworkError(): ApiResponseV2.NetworkError<S, E>?{
    if(this is ApiResponseV2.NetworkError){
        return this
    }
    return null
}

fun <S, E> ApiResponseV2<S, E>.asUnknownError(): ApiResponseV2.UnknownError<S, E>?{
    if(this is ApiResponseV2.UnknownError){
        return this
    }
    return null
}

fun<S, E> ApiResponseV2<S, E>.isSuccess() = this is ApiResponseV2.Success
fun<S, E> ApiResponseV2<S, E>.isSuccessEmpty() = (this is ApiResponseV2.Success) && this.body == null
fun<S, E> ApiResponseV2<S, E>.isError() = this is ApiResponseV2.Error

fun<S, E> ApiResponseV2<S, E>.getOrNull(): S? {
    return when (this) {
        is ApiResponseV2.Success -> this.body
        else -> null
    }
}

/**
 * applies the given function, if network response
 * state is Success
 */
inline fun <S, E> ApiResponseV2<S, E>.onSuccess(
    crossinline onResult: S.() -> Unit
): ApiResponseV2<S, E> {
    if (this is ApiResponseV2.Success) {
        onResult(this.body)
    }
    return this
}

///**
// * applies the given function in case of successful response,
// * however it is transforming the body into another type
// */
//inline fun <S, E, V> ApiResponseV2<S, E>.onSuccess(
//    mapper: (S) -> V,
//    crossinline onResult: V.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Success) {
//        onResult(mapper(this.body))
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedSuccess(
//    crossinline onResult: suspend S.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Success) {
//        onResult(this.body)
//    }
//    return this
//}
//suspend inline fun <S, E, V> ApiResponseV2<S, E>.onSuspendedSuccess(
//    mapper: (S) -> V,
//    crossinline onResult: suspend V.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Success) {
//        onResult(mapper(this.body))
//    }
//    return this
//}
//

/**
 * applies the given function, if network response
 * state is Success
 */
//inline fun <S, E> ApiResponseV2<S, E>.onError(
//    crossinline onResult:  ApiResponseV2.Error<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Error) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedError(
//    crossinline onResult: suspend ApiResponseV2.Error<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Error) {
//        onResult(this)
//    }
//    return this
//}
//
///**
// * applies the given function, if network response
// * state is Success
// */
//inline fun <S, E> ApiResponseV2<S, E>.onHttpError(
//    crossinline onResult:  ApiResponseV2.Error.HttpError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Error.HttpError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedHttpError(
//    crossinline onResult: suspend ApiResponseV2.Error.HttpError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Error.HttpError) {
//        onResult(this)
//    }
//    return this
//}
//
///**
// * applies the given function, if network response
// * state is Success
// */
//inline fun <S, E> ApiResponseV2<S, E>.onNetworkError(
//    crossinline onResult:  ApiResponseV2.Error.NetworkError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Error.NetworkError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedNetworkError(
//    crossinline onResult: suspend ApiResponseV2.Error.NetworkError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Error.NetworkError) {
//        onResult(this)
//    }
//    return this
//}
//
///**
// * applies the given function, if network response
// * state is Success
// */
//inline fun <S, E> ApiResponseV2<S, E>.onUnknownError(
//    crossinline onResult:  ApiResponseV2.Error.UnknownError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Error.UnknownError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedUnknownError(
//    crossinline onResult: suspend ApiResponseV2.Error.UnknownError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is ApiResponseV2.Error.UnknownError) {
//        onResult(this)
//    }
//    return this
//}
//
//inline fun<S, E> ApiResponseV2<S, E>.toFlow(): Flow<T>{
//    return if(this is ApiResponseV2.Success){
//        flowOf(this.body)
//    }else{
//        emptyFlow()
//    }
//}
//
//inline fun <T: Any, R> ApiResponseV2<S, E>.toFlow(
//    crossinline transformer: T.() -> R
//): Flow<R>{
//    return if(this is ApiResponseV2.Success){
//        flowOf(this.body.transformer())
//    }else{
//        emptyFlow()
//    }
//}
//suspend inline fun <T: Any, R> ApiResponseV2<S, E>.toSuspendFlow(
//    crossinline transformer: suspend T.() -> R
//): Flow<R>{
//    return if(this is ApiResponseV2.Success){
//        flowOf(this.body.transformer())
//    }else{
//        emptyFlow()
//    }
//}
