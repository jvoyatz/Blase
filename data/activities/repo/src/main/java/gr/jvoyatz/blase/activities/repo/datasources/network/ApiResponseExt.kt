package gr.jvoyatz.blase.activities.repo.datasources.network


fun <S, E> ApiResponseV2<S, E>.asSuccess():Success<S, E>?{
    if(this is Success){
        return this
    }
    return null
}

fun <S, E> ApiResponseV2<S, E>.asError(): Error<S, E>?{
    if(this is Error){
        return this
    }
    return null
}

fun <S, E> ApiResponseV2<S, E>.asHttpError(): HttpError<S, E>?{
    if(this is HttpError){
        return this
    }
    return null
}

fun <S, E> ApiResponseV2<S, E>.asNetworkError(): NetworkError<S, E>?{
    if(this is NetworkError){
        return this
    }
    return null
}

fun <S, E> ApiResponseV2<S, E>.asUnknownError(): UnknownError<S, E>?{
    if(this is UnknownError){
        return this
    }
    return null
}

fun<S, E> ApiResponseV2<S, E>.isSuccess() = this is Success
fun<S, E> ApiResponseV2<S, E>.isSuccessEmpty() = (this is Success) && this.body == null
fun<S, E> ApiResponseV2<S, E>.isError() = this is Error

fun<S, E> ApiResponseV2<S, E>.getOrNull(): S? {
    return when (this) {
        is Success -> this.body
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
    if (this is Success) {
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
//    if (this is Success) {
//        onResult(mapper(this.body))
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedSuccess(
//    crossinline onResult: suspend S.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is Success) {
//        onResult(this.body)
//    }
//    return this
//}
//suspend inline fun <S, E, V> ApiResponseV2<S, E>.onSuspendedSuccess(
//    mapper: (S) -> V,
//    crossinline onResult: suspend V.() -> Unit
//): ApiResponseV2<S, E> {
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
//inline fun <S, E> ApiResponseV2<S, E>.onError(
//    crossinline onResult:  Error<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is Error) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedError(
//    crossinline onResult: suspend Error<T>.() -> Unit
//): ApiResponseV2<S, E> {
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
//inline fun <S, E> ApiResponseV2<S, E>.onHttpError(
//    crossinline onResult:  Error.HttpError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is Error.HttpError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedHttpError(
//    crossinline onResult: suspend Error.HttpError<T>.() -> Unit
//): ApiResponseV2<S, E> {
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
//inline fun <S, E> ApiResponseV2<S, E>.onNetworkError(
//    crossinline onResult:  Error.NetworkError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is Error.NetworkError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedNetworkError(
//    crossinline onResult: suspend Error.NetworkError<T>.() -> Unit
//): ApiResponseV2<S, E> {
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
//inline fun <S, E> ApiResponseV2<S, E>.onUnknownError(
//    crossinline onResult:  Error.UnknownError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is Error.UnknownError) {
//        onResult(this)
//    }
//    return this
//}
//
//suspend inline fun <S, E> ApiResponseV2<S, E>.onSuspendedUnknownError(
//    crossinline onResult: suspend Error.UnknownError<T>.() -> Unit
//): ApiResponseV2<S, E> {
//    if (this is Error.UnknownError) {
//        onResult(this)
//    }
//    return this
//}
//
//inline fun<S, E> ApiResponseV2<S, E>.toFlow(): Flow<T>{
//    return if(this is Success){
//        flowOf(this.body)
//    }else{
//        emptyFlow()
//    }
//}
//
//inline fun <T: Any, R> ApiResponseV2<S, E>.toFlow(
//    crossinline transformer: T.() -> R
//): Flow<R>{
//    return if(this is Success){
//        flowOf(this.body.transformer())
//    }else{
//        emptyFlow()
//    }
//}
//suspend inline fun <T: Any, R> ApiResponseV2<S, E>.toSuspendFlow(
//    crossinline transformer: suspend T.() -> R
//): Flow<R>{
//    return if(this is Success){
//        flowOf(this.body.transformer())
//    }else{
//        emptyFlow()
//    }
//}
