package gr.jvoyatz.blase.core.network.v1.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.blase.core.network.v1.interceptors.CustomLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

/**
 * In Kotlin, modules that only contain @Provides functions can be object classes.
 * This way, providers get optimized and almost in-lined in generated code.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi():Moshi{
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(CustomLoggingInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
}