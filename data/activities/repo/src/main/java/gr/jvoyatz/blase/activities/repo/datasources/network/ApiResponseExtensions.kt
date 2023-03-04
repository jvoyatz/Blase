package gr.jvoyatz.blase.activities.repo.datasources.network

import gr.jvoyatz.blase.core.network.v1.ApiResponse

/**
 *
 * Func used to map the response into the given type in case it is successful
 *
 */

suspend inline fun <T : Any> ApiResponse<T>.onSuspendedSuccess(
    crossinline onResult: suspend T.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.ApiSuccess) {
        onResult(this.response)
    }
    return this
}


inline fun <T : Any> ApiResponse<T>.onSuccess(
    crossinline onResult: T.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.ApiSuccess) {
        onResult(this.response)
    }
    return this
}
