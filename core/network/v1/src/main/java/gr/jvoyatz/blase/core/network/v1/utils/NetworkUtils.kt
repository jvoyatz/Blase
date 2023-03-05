package gr.jvoyatz.blase.core.network.v1

import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

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
suspend fun <T: Any> safeSuspendableApiCall(execute: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = execute()
        val body = response.body()

        Timber.d("response body for this request -> $body")
        if(response.isSuccessful && body != null){
            ApiResponse.ApiSuccess(body)
        }else if(response.isSuccessful){
            ApiResponse.ApiSuccessEmpty()
        }else{
            ApiResponse.ApiError(response.code(), response.message())
        }
    }catch (e: HttpException){
        Timber.e(e, "http exception")
        ApiResponse.ApiError(e.code(), e.message)
    } catch (e: Throwable){
        Timber.e(e, "unexpected exception")
        ApiResponse.ApiException(e)
    }
}

/*suspend*/ fun <T: Any> safeApiCall(execute: /*suspend*/ () -> Response<T>): ApiResponse<T> {
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