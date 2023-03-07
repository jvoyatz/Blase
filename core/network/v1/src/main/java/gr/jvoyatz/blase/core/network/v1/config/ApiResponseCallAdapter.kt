package gr.jvoyatz.blase.core.network.v1.config

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * Adapts and delegates a retrofit normal Call to our ApiResponseCall
 */
internal class ApiResponseCallAdapter<E>(
    private val successType: Type,
    private val errorConverter: Converter<ResponseBody, E>,
): CallAdapter<Type, Call<ApiResponse<Type, E>>> {

    override fun responseType(): Type = successType

    /**
     * We create a new instance of our NetworkCall
     */
    override fun adapt(call: Call<Type>): Call<ApiResponse<Type, E>> {
        return ApiResponseCall(call, successType, errorConverter)
    }
}