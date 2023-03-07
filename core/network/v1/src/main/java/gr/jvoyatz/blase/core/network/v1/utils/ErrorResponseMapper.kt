package gr.jvoyatz.blase.core.network.v1.utils

import com.squareup.moshi.Moshi
import retrofit2.HttpException

interface RetrofitErrorResponseMapper{
    val moshi: Moshi
}

inline fun <reified E> RetrofitErrorResponseMapper.map(httpException: HttpException):E? {
    return try {
        httpException.response()?.errorBody()?.source()?.let {
            moshi.adapter(E::class.java).fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}

class RetrofitErrorResponseMapperImpl(override val moshi: Moshi) : RetrofitErrorResponseMapper