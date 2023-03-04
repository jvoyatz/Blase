package gr.jvoyatz.blase.activities.repo

import gr.jvoyatz.blase.activities.repo.datasources.network.BoredApiClient
import gr.jvoyatz.blase.activities.repo.datasources.network.onSuccess
import gr.jvoyatz.blase.activities.repo.datasources.network.onSuspendedSuccess
import gr.jvoyatz.blase.activities.repo.mapper.BoredActivityDtoMapper.mapToDomainModel
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.blase.logging.LogEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher

class BoredActivityRepositoryImpl(
    private val apiClient: BoredApiClient,
    //private val dbSource: ActivityDbSource
):BoredActivityRepository{
    override suspend fun getNewActivity(): Flow<BoredActivity> {
        LogEvent.d("fetching new activity, thread is ${Thread.currentThread()}")

        return flow {
            apiClient.getRandomActivity()
                .onSuspendedSuccess {
                    LogEvent.logThread()
                    LogEvent.d("this $this")
                    emit(this.mapToDomainModel())
                }
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun saveActivity() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteActivity(key: String) {
        TODO("Not yet implemented")
    }

    override suspend fun isActivitySaved(key: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteActivities() {
        TODO("Not yet implemented")
    }
}