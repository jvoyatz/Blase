package gr.jvoyatz.blase.activities.repo.datasources.local.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.blase.activities.repo.datasources.local.BlaseDatabase
import gr.jvoyatz.blase.activities.repo.datasources.local.BoredActivityDao
import gr.jvoyatz.blase.activities.repo.datasources.local.BoredDbClient
import gr.jvoyatz.blase.activities.repo.datasources.local.BoredDbClientImpl
import gr.jvoyatz.blase.activities.repo.datasources.network.BoredApiClientImpl
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ):BlaseDatabase = BlaseDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideBoredActivityDao(
        db: BlaseDatabase
    ):BoredActivityDao = db.boredActivityDao()

    @Singleton
    @Provides
    fun provideBoredDbClient(dao: BoredActivityDao): BoredDbClient = BoredDbClientImpl(dao)
}