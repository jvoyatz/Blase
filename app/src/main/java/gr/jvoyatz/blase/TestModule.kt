package gr.jvoyatz.blase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
object TestModule {
    @Provides
    fun provides():BoredActivityRepository{
        return BoredActivityRepositoryImpl()
    }
}