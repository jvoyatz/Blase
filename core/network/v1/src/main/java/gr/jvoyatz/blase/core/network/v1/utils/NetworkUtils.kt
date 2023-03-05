package gr.jvoyatz.blase.core.network.v1.utils

import gr.jvoyatz.blase.core.network.v1.ApiResponse
import gr.jvoyatz.blase.core.network.v1.ApiResponse.*
import gr.jvoyatz.blase.core.network.v1.ApiResponse.ApiError.HttpError
import gr.jvoyatz.blase.core.network.v1.ApiResponse.ApiError.NetworkError
import gr.jvoyatz.blase.core.network.v1.ApiResponse.ApiError.UnknownError
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NetworkUtils
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
suspend fun <T: Any> safeApiCall(execute: /*suspend*/ () -> Response<T>): ApiResponse<T> {
    return try {
        val response = execute()
        val body = response.body()

        if(response.isSuccessful && body != null && body != Unit){
            ApiSuccess(body)
        }else if(response.isSuccessful){
            ApiSuccessEmpty()
        }else{
            HttpError(response.code(), response.message())
        }
    }catch (e: HttpException){
        HttpError(e.code(), e.message)
    } catch (e: IOException){
        NetworkError(e)
    } catch (e: Exception){ //distinguish from ioexceptions
        UnknownError(e)
    }
}

suspend fun <T: Any> safeRawApiCall(apiCall: suspend () -> T?): ApiResponse<T> {
    return try{
        val response = apiCall()
        println("response is $response")
        if(response == null || response is Unit){ //handling the case where response is null due to 204 or Unit
            ApiSuccessEmpty()
        }else{
            ApiSuccess(response)
        }
    }catch (t: Throwable){
        when(t){
            is IOException -> NetworkError(t)
            is HttpException -> HttpError(t.code(), t.message())
            else -> UnknownError(t)
        }
    }
}

