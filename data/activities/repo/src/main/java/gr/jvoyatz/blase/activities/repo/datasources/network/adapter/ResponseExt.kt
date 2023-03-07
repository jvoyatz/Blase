package gr.jvoyatz.blase.activities.repo.datasources.network.adapter

import gr.jvoyatz.blase.activities.repo.datasources.network.Success
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type


/**
 * Parses a throwable of type [HttpException] and converts it into ApiResponse
 *
 * 1) IoException -> NetworkError
 * 2) HttpException -> HttpError
 * 3) everything else -> UnknownError
 */
internal fun <S, E> Throwable.asApiResponse(
    successType: Type,
    errorConverter: Converter<ResponseBody, E>,
): ApiResponse<S, E> {
    return when (this) {
        is HttpException -> {
            val response = response()
            if (response != null) {
                @Suppress("UNCHECKED_CAST")
                response.asApiResponse(successType, errorConverter) as ApiResponse<S, E>
            } else {
                ApiResponse.httpError()
            }
        }
        is IOException -> ApiResponse.networkError(this)
        else -> ApiResponse.unknownError(this)
    }
}

/**
 * Maps a Retrofit Response to ApiResponse.
 * Extension function for Retrofit's [Response]
 *
 * Takes as parameters the successType [Type] as found in Retrofit call, and,
 * errorConverter to deserialize the errorBody
 */
internal fun <S, E> Response<S>.asApiResponse(
    successType: Type,
    errorConverter: Converter<ResponseBody, E>
): ApiResponse<S, E> {
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
): ApiResponse<S, E> {

    //can be null
    val body: S? = response.body()
    if (body != null) return ApiResponse.success<S, E>(body)

    if (successType === Unit::class.java) {
        @Suppress("UNCHECKED_CAST")
        return Success<Unit, E>(Unit) as ApiResponse<S, E>
    }

    return ApiResponse.httpError(response.code(), null)
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
    errorConverter: Converter<ResponseBody, E>
): ApiResponse<S, E> = response.let {
    val errorBody = it.errorBody() ?: return ApiResponse.httpError(it.code())
    try {
        errorConverter.convert(errorBody).let { body ->
            ApiResponse.httpError(it.code(), body)
        }
    } catch (e: Exception) {
        ApiResponse.unknownError(e)
    }
}
