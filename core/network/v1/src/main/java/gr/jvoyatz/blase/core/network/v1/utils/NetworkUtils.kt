package gr.jvoyatz.blase.core.network.v1

import retrofit2.HttpException
import retrofit2.Response

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
suspend fun <T: Any> safeApiCall(execute: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = execute()
        val body = response.body()

        if(response.isSuccessful && body != null){
            ApiResponse.ApiSuccess(body)
        }else if(response.isSuccessful){
            ApiResponse.ApiSuccessEmpty()
        }else{
            ApiResponse.ApiError(response.code(), response.message())
        }
    }catch (e: HttpException){
      ApiResponse.ApiError(e.code(), e.message)
    } catch (e: Throwable){
        ApiResponse.ApiException(e)
    }
}