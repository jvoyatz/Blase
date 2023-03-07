package gr.jvoyatz.blase.core.network.v1.utils

import gr.jvoyatz.blase.core.network.v1.ApiResponseV2
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type


/**
 * Receives a lambda functions, which returns
 * a retrofit response.
 * After this functions has been execute, the response is parsed
 * and a response containing the state of the HttpResponse is returned
 * see ApiResponse
 *
 * A downside using this functions is that you need to include it in every repository.
 * It could be reused (and improved) by using a retrofit call adapter
 */
suspend inline fun <S, reified E> safeApiCall(apiCall: () -> S?, errorResponseMapper: RetrofitErrorResponseMapper): ApiResponseV2<S, E> {
    return try {
        Timber.d("current thread " + Thread.currentThread())
        val response = apiCall()
        println("response is $response")
        Timber.d("current thread 2 " + Thread.currentThread())

        if (response == null || response is Unit) { //handling the case where response is null due to 204 or Unit
            @Suppress("UNCHECKED_CAST")
            ApiResponseV2.success<Unit, E>(Unit) as ApiResponseV2<S, E>
        } else {
            ApiResponseV2.success(response)
        }

    } catch (t: Throwable) {
        when (t) {
            is IOException -> ApiResponseV2.networkError(t)
            is HttpException -> ApiResponseV2.httpError(t.code(), errorResponseMapper.map(t))
            else -> ApiResponseV2.networkError(t)
        }
    }
}
suspend fun <S, E> safeApiCall(
    execute: suspend () -> Response<S>,
    successType: Type,
    errorConverter: ErrorBodyMapper<E>,
): ApiResponseV2<S, E> {
    return execute().asApiResponse(successType, errorConverter)
}

/**
 * Parses a throwable and converts it into ApiResponse
 * 1) IoException -> NetworkError
 * 2) HttpException -> HttpError
 * 3) everything else -> UnknownError
 */
internal fun <S, E> Throwable.asApiResponse(
    successType: Type,
    errorConverter: ErrorBodyMapper<E>,
): ApiResponseV2<S, E> = parseThrowable(successType, errorConverter)

private fun <S, E> Throwable.parseThrowable(
    successType: Type,
    errorConverter: ErrorBodyMapper<E>,
): ApiResponseV2<S, E> {
    return when (this) {
        is HttpException -> {
            val response = response()
            if (response != null) {
                @Suppress("UNCHECKED_CAST")
                response.asApiResponse(successType, errorConverter) as ApiResponseV2<S, E>
            } else {
                ApiResponseV2.httpError<S, E>()
            }
        }
        is IOException -> ApiResponseV2.networkError<S, E>(this)
        else -> ApiResponseV2.unknownError(this)
    }
}


/**
 * Maps a Retrofit Response to ApiResponse
 */
internal fun <S, E> Response<S>.asApiResponse(
    successType: Type,
    errorConverter: ErrorBodyMapper<E>
): ApiResponseV2<S, E> {
    return if (isSuccessful) {
        parseSuccessfulResponse(this, successType)
    } else {
        parseUnsuccessfulResponse(this, errorConverter)
    }
}

/**
 * Check whether response body is null or not.
 * If not, then return ApiResponse.Success
 * If null, and success type is Unit, return Success<Unit>,
 * else return HttpError
 */
private fun <S, E> parseSuccessfulResponse(
    response: Response<S>,
    successType: Type,
): ApiResponseV2<S, E> {

    //can be null
    val body: S? = response.body()
    if (body != null) return ApiResponseV2.success(body)

    if (successType === Unit::class.java) {
        @Suppress("UNCHECKED_CAST")
        return ApiResponseV2.Success<Unit, E>(Unit) as ApiResponseV2<S, E>
    }

    return ApiResponseV2.httpError(response.code(), null)
}

/**
 * Parses the response and returns an ApiResponse.Error instance
 *
 * Check if errorbody is available and deserialize it into a type
 * In case this task happens successfully, then HttpError is returned
 * Otherwise we return an unknownError, in case of an exception
 */
private fun <S, E> parseUnsuccessfulResponse(
    response: Response<S>,
   // errorConverter: Converter<ResponseBody, E>,
   errorMapper: ErrorBodyMapper<E>
): ApiResponseV2<S, E> {

    return response.let {
        val errorBody = it.errorBody() ?: return ApiResponseV2.httpError(it.code())

        try {
            errorMapper().let { body ->
                ApiResponseV2.httpError(it.code(), body)
            }
        } catch (e: Exception) {
            ApiResponseV2.unknownError(e)
        }
    }
}
//private fun <S, E> parseUnsuccessfulResponse(
//    response: Response<S>,
//    errorConverter: Converter<ResponseBody, E>,
//): ApiResponseV2.Error<S, E> {
//
//    return response.let {
//        val errorBody = it.errorBody() ?: return ApiResponseV2.httpError(it.code())
//
//        try {
//            errorConverter.convert(errorBody).let { erb ->
//                ApiResponseV2.httpError(it.code(), erb)
//            }
//        } catch (e: Exception) {
//            ApiResponseV2.unknownError(e)
//        }
//    }
//}
fun interface ErrorBodyMapper<E>: () -> E

