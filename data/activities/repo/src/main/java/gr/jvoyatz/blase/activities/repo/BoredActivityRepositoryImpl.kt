package gr.jvoyatz.blase.activities.repo

import android.util.Log
import gr.jvoyatz.blase.activities.repo.datasources.local.BoredDbClient
import gr.jvoyatz.blase.activities.repo.datasources.network.BoredApiClient
import gr.jvoyatz.blase.activities.repo.mapper.BoredActivityDtoMapper.mapToDomainModel
import gr.jvoyatz.blase.activities.repo.mapper.BoredEntityMapper.mapFromDomainModel
import gr.jvoyatz.blase.activities.repo.mapper.BoredEntityMapper.mapToDomainModel
import gr.jvoyatz.blase.activities.repo.mapper.mapList
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.models.BoredException
import gr.jvoyatz.blase.domain.models.FavoriteBoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber

class BoredActivityRepositoryImpl(
    private val apiClient: BoredApiClient,
    private val dbClient: BoredDbClient
):BoredActivityRepository{
    override suspend fun getNewActivity(): Flow<FavoriteBoredActivity> {
        Timber.d("fetching new activity, thread is ${Thread.currentThread()}")

        return flow {
            apiClient.getRandomActivity()
                .mapToDomainModel()
                .let {
                    //check if activity is already saved
                    val isSaved = isActivitySaved(it.key)
                    Timber.d("is saved ?? $isSaved")
                    emit(FavoriteBoredActivity(isSaved, it))
                }
        }.catch { throw BoredException(it) }
        .flowOn(Dispatchers.IO)
    }
    override suspend fun saveActivity(boredActivity: BoredActivity): Flow<Unit> {
        Timber.d("saveActivity() called with: boredActivity = " + boredActivity)
        return dbClient.saveActivity(boredActivity.mapFromDomainModel())
    }

    override suspend fun deleteActivity(boredActivity: BoredActivity): Flow<Unit> {
        return flow {
            dbClient.deleteActivity(boredActivity.mapFromDomainModel())
        }
    }

    override suspend fun isActivitySaved(key: Long):Boolean {
        return dbClient.isActivitySaved(key)
    }

    override suspend fun getFavoriteActivities(): Flow<List<BoredActivity>> {
        return dbClient.getFavoriteActivities()
            .map { entities ->
                    entities.mapList { it.mapToDomainModel() }
            }
    }
}

private const val TAG = "BoredActivityRepository"