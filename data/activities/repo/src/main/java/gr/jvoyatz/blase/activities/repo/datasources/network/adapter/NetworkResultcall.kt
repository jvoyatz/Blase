package gr.jvoyatz.blase.activities.repo.datasources.network.adapter

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResultCall<S, E>(
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
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                Timber.d("onFailure() called with: call = " + call + ", t = " + t)
                val apiResponse = t.asApiResponse<S, E>(successType, errorConverter)
                callback.onResponse(this@NetworkResultCall, Response.success(apiResponse))
            }
        })
    }

    override fun execute(): Response<ApiResponse<S, E>> = throw NotImplementedError()
    override fun clone(): Call<ApiResponse<S, E>> = NetworkResultCall(proxy.clone(), successType, errorConverter)
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() { proxy.cancel() }
}


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
        return NetworkResultCall(call, successType, errorConverter)
    }
}

class NetworkResultCallAdapterFactory: CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (returnType !is ParameterizedType) {
            //must be parameterized
            Timber.e("return type must be parameterized, something like Call<YourType>")
            return null
        }

        if (getRawType(returnType) != Call::class.java) {
            Timber.e("return type must be something of Call class type, instead you provided $returnType")
            return null
        }
        Timber.i("Your return type is $returnType")

        val callType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(callType) != ApiResponse::class.java) {
            Timber.e("Your wrapped type inside Call<T> is not ApiResponse")
            return null
        }

        if(callType !is ParameterizedType){
            Timber.e("call type is not parameterized")
            return null
        }


        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
        //get error declared type and its converter
        val errorType = getParameterUpperBound(1, callType)
        val errorConverter = retrofit.nextResponseBodyConverter<Any>(null, errorType, annotations)

        Timber.i("2. Your result type is $returnType")
        return ApiResponseCallAdapter<Any>(resultType, errorConverter)
    }

    companion object {
        fun create(): NetworkResultCallAdapterFactory = NetworkResultCallAdapterFactory()
    }
}