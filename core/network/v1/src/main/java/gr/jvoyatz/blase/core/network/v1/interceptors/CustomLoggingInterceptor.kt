package gr.jvoyatz.blase.core.network.v1.interceptors

import gr.jvoyatz.blase.logging.LogEvent
import okhttp3.Interceptor
import okhttp3.Response

internal class CustomLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        LogEvent.i("$request")
        return chain.proceed(request)
    }
}