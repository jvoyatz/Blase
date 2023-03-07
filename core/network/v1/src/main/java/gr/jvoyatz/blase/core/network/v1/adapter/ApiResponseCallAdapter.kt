package gr.jvoyatz.blase.core.network.v1.adapter

import gr.jvoyatz.blase.core.network.v1.ApiResponseV2
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * Adapts the call to ApiResponse
 * [CallAdapter]
 */
class ApiResponseCallAdapter<S : Any, E : Any>(
    private val successType: Type,
    private val errorConverter: Converter<ResponseBody, E>,
) : CallAdapter<S, Call<ApiResponseV2<S, E>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<ApiResponseV2<S, E>> =
        ApiResponseCall(call, errorConverter, successType)
}