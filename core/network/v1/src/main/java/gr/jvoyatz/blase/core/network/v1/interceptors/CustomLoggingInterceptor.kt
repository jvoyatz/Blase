package gr.jvoyatz.blase.core.network.v1.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

internal class CustomLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        Timber.w("Request [$request]")

        return chain.proceed(request).also {
            Timber.w("Response [$it]")
        }
    }
}