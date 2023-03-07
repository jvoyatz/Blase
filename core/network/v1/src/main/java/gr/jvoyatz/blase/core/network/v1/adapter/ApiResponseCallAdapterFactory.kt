package gr.jvoyatz.blase.core.network.v1.adapter

import gr.jvoyatz.blase.core.network.v1.ApiResponseV2
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Adapts the call to ApiResponse
 * [CallAdapter]
 */
class ApiResponseCallAdapterFactory
    private constructor(): CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {

        if(returnType !is ParameterizedType){
            //must be parameterized
            Timber.e("return type must be parameterized, something like Call<YourType>")
            return null
        }

        if(getRawType(returnType) != Call::class.java){
            Timber.e("return type must be something of Call class type, instead you provided $returnType")
            return null
        }
        Timber.i("Your return type is $returnType")

        val callType = getParameterUpperBound(0, returnType as ParameterizedType)
        if(getRawType(callType) != ApiResponseV2::class.java){
            Timber.e("Your wrapped type inside Call<T> is not ApiResponse")
            return null
        }

        if(callType !is ParameterizedType){
            Timber.e("call type is not parameterized")
            return null
        }

        //get error declared type and its converter
        val errorType = getParameterUpperBound(1, callType)
        val errorConverter = retrofit.nextResponseBodyConverter<Any>(null, errorType, annotations)

        Timber.i("Your result type is $returnType")
        return ApiResponseCallAdapter<Any, Any>(returnType, errorConverter)
    }

    companion object{
        fun create(): ApiResponseCallAdapterFactory = ApiResponseCallAdapterFactory()
    }
}