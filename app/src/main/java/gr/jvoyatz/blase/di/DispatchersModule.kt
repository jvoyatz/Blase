package gr.jvoyatz.blase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.core.common.AppDispatchers
import gr.jvoyatz.core.common.AppDispatchersImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatchersModule {
    @Singleton
    @Binds
    abstract fun bindAppDispatchers(impl: AppDispatchersImpl): AppDispatchers
}