package gr.jvoyatz.blase.activities.repo

import android.util.Log
import gr.jvoyatz.blase.activities.repo.datasources.local.BoredDbClient
import gr.jvoyatz.blase.activities.repo.datasources.network.BoredApiClient
import gr.jvoyatz.blase.activities.repo.datasources.network.onSuspendedSuccess
import gr.jvoyatz.blase.activities.repo.mapper.BoredActivityDtoMapper.mapToDomainModel
import gr.jvoyatz.blase.activities.repo.mapper.BoredEntityMapper.mapFromDomainModel
import gr.jvoyatz.blase.activities.repo.mapper.BoredEntityMapper.mapToDomainModel
import gr.jvoyatz.blase.activities.repo.mapper.mapList
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.blase.logging.LogEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class BoredActivityRepositoryImpl(
    private val apiClient: BoredApiClient,
    private val dbClient: BoredDbClient
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

    override suspend fun saveActivity(boredActivity: BoredActivity): Flow<Unit> {
        Log.d(TAG, "saveActivity() called with: boredActivity = $boredActivity")
        return dbClient.saveActivity(boredActivity.mapFromDomainModel())
    }

    override suspend fun deleteActivity(key: String) {
        TODO("Not yet implemented")
    }

    override suspend fun isActivitySaved(key: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteActivities(): Flow<List<BoredActivity>> {
        return dbClient.getFavoriteActivities()
            .map { entities ->
                    entities.mapList { it.mapToDomainModel() }
            }.also {
                Log.d(TAG,"hereeee!!!!!!!!! $it")
            }
    }
}

private const val TAG = "BoredActivityRepository"