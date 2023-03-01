package gr.jvoyatz.blase.core.network.v1

/**
* It represents the state of the Retrofit response
*/
sealed class ApiResponse<T: Any> {
    /**
     * Successful response with data
     */
    data class ApiSuccess<T : Any>(val response: T): ApiResponse<T>()

    /**
     * Handles successful response containing no data
     */
    /*data*/ class ApiSuccessEmpty<T: Any> : ApiResponse<T>()

    /**
     * Received a response with an error
     */
    data class ApiError<T : Any>(val code: Int, val message: String?): ApiResponse<T>()

    /**
     * Network error, IO Exception
     */
    data class ApiException<T: Any>(val e: Throwable): ApiResponse<T>()
}