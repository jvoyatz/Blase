package gr.jvoyatz.blase.core.network.v1

/**
* It represents the state of the Retrofit response.
 *
 * The possible states are:
 *  success or error
 *
 *  S represents the body of a successful response
 *  E represents the body of the error response
 *
 *  When the response code is in the range 200-299 is considered as successful,
 *  otherwise error.
 *
 *  In case of a successful request as well as a successful deserialization of the response body
 *  then the response is ApiResponse.Success. When a response does not contain a
 *  body, then the S type should be declared as Unit.
 *
 *  When an error has been returned:
 *  1) HttpError with deserialized the error body
 *  2) IOExceptions are being represented as NetworkError
 *  3) For the rest errors/exceptions, UnknownError is being returned
*/
sealed interface ApiResponseV2<S, E> {
    /**
     * Successful response with data
     * In case where data are not available, then declare the S as Unit or VoidResponse
     */
    data class Success<S, E>(val body: S): ApiResponseV2<S, E>


    sealed interface Error<S, E> : ApiResponseV2<S, E>

    /**
     * Received a response with an error
     */
    data class HttpError<S, E>(val code: Int?, val errorBody: E?): Error<S, E>

    /**
     * Network error, IO Exception
     */
    data class NetworkError<S, E>(val error: Throwable?): Error<S, E>

    /**
     * Unknown Error during request
     */
    data class UnknownError<S, E>(val error: Throwable?):Error<S, E>

    companion object{
        fun <S, E> httpError(code: Int? = null, errorBody: E? = null): ApiResponseV2<S, E> {
            return HttpError<S, E>(code, errorBody)
        }
        fun <S, E> unknownError(throwable: Throwable? = null): Error<S, E> = UnknownError(throwable)
        fun <S, E> networkError(throwable: Throwable? = null): Error<S, E> = NetworkError(throwable)
        fun <S, E> success(body: S): ApiResponseV2.Success<S, E> = Success(body)
    }
}

/**
 * An alias when not expecting response body
 */
typealias VoidResponse<E> = ApiResponseV2<Unit, E>