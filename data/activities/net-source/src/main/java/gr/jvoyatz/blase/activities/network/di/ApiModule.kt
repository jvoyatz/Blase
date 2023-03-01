package gr.jvoyatz.blase.activities.network.di

import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.blase.activities.network.BoredApiClient
import gr.jvoyatz.blase.activities.network.BoredApiClientImpl
import gr.jvoyatz.blase.activities.network.api.BoredApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.boredapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideBoredApiService(retrofit: Retrofit): BoredApiService{
        return retrofit.create(BoredApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideBoredApiClient(boredApiService: BoredApiService): BoredApiClient{
        return BoredApiClientImpl(boredApiService)
    }

//    @Module
//    @InstallIn(SingletonComponent::class)
//    abstract class ApiBindsModule{
//        @Binds
//        @Singleton
//        abstract fun bindBoredApiClient(boredApiClientImpl: BoredApiClientImpl): BoredApiClient
//    }
}