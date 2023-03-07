package gr.jvoyatz.blase.core.network.v1.adapter

import com.squareup.moshi.Moshi
import gr.jvoyatz.blase.core.network.v1.ApiResponseV2
import gr.jvoyatz.blase.core.network.v1.utils.ErrorBodyMapper
import gr.jvoyatz.blase.core.network.v1.utils.RetrofitErrorResponseMapper
import gr.jvoyatz.blase.core.network.v1.utils.asApiResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Wraps a Retrofit call and returns a response object
 * of ApiResponse Type
 */
internal class ApiResponseCall<S, E>(
    private val call: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>,
    private val successType: Type
): Call<ApiResponseV2<S, E>> {

    override fun clone(): Call<ApiResponseV2<S, E>> = ApiResponseCall(call, errorConverter, successType)
    override fun isExecuted() = call.isExecuted
    override fun cancel() = call.cancel()
    override fun isCanceled() = call.isCanceled

    override fun request(): Request = call.request()
    override fun timeout(): Timeout = call.timeout()

    override fun execute(): Response<ApiResponseV2<S, E>> {
        return call.execute().let {
            ErrorBodyMapper {
                errorConverter.convert()
            }
            Response.success(it.asApiResponse(successType))
        }
    }

    override fun enqueue(callback: Callback<ApiResponseV2<S, E>>) {
        call.enqueue(object: Callback<S>{
            override fun onResponse(call: Call<S>, response: Response<S>) {
                response.asApiResponse(successType).let {
                    callback.onResponse(this@ApiResponseCall, Response.success(it))
                }
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                t.asApiResponse<S, E>(successType, errorConverter).let {
                    callback.onResponse(this@ApiResponseCall, Response.success(it))
                }
            }
        })
    }
}

//override fun enqueue(callback: Callback<ApiResponseV2<S, E>>) {
//    call.enqueue(object: Callback<S>{
//        override fun onResponse(call: Call<S>, response: Response<S>) {
//            response.asApiResponse(successType, errorConverter).let {
//                callback.onResponse(this@ApiResponseCall, Response.success(it))
//            }
//        }
//
//        override fun onFailure(call: Call<S>, t: Throwable) {
//            t.asApiResponse(successType, errorConverter).let {
//                callback.onResponse(this@ApiResponseCall, Response.success(it))
//            }
//        }
//
//    })
//