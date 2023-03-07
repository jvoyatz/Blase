package gr.jvoyatz.blase.core.network.v1.config

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import timber.log.Timber
import java.lang.reflect.Type

internal class ApiResponseCall<S, E>(
    private val proxy: Call<S>,
    private val successType: Type,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<ApiResponse<S, E>> {

    override fun enqueue(callback: Callback<ApiResponse<S, E>>) {
        Timber.d("enqueue() called with: callback = " + callback)
        proxy.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                //val networkResult = handleApiCall<S, E> { response }
                val networkResult = response.asApiResponse(successType, errorConverter)
                callback.onResponse(this@ApiResponseCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                Timber.d("onFailure() called with: call = " + call + ", t = " + t)
                val apiResponse = t.asApiResponse<S, E>(successType, errorConverter)
                callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
            }
        })
    }

    override fun execute(): Response<ApiResponse<S, E>> = throw NotImplementedError()
    override fun clone(): Call<ApiResponse<S, E>> = ApiResponseCall(proxy.clone(), successType, errorConverter)
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() { proxy.cancel() }
}