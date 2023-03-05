package gr.jvoyatz.blase.activities.repo.datasources.local.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.blase.activities.repo.datasources.local.BoredDbClient
import gr.jvoyatz.blase.activities.repo.datasources.local.BoredDbClientImpl
import gr.jvoyatz.blase.database.BlaseDatabase
import gr.jvoyatz.blase.database.BoredActivityDao
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideBoredActivityDao(
        db: BlaseDatabase
    ): BoredActivityDao = db.boredActivityDao()

    @Singleton
    @Provides
    fun provideBoredDbClient(dao: BoredActivityDao): BoredDbClient = BoredDbClientImpl(dao)
}