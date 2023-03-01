package gr.jvoyatz.blase.activities.repo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.blase.activities.network.BoredApiClient
import gr.jvoyatz.blase.activities.repo.BoredActivityRepositoryImpl
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ActivitiesDataModule {

    @Provides
    @Singleton
    fun provideBoredActivityRepository(boredApiClient: BoredApiClient):BoredActivityRepository{
        return BoredActivityRepositoryImpl(boredApiClient)
    }
}