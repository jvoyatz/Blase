package gr.jvoyatz.blase.activities.repo

import gr.jvoyatz.blase.activities.repo.datasources.local.BoredDbClient
import gr.jvoyatz.blase.activities.repo.datasources.network.BoredApiClient
import gr.jvoyatz.blase.activities.repo.mapper.BoredEntityMapper.mapFromDomainModel
import gr.jvoyatz.blase.activities.repo.mapper.BoredEntityMapper.mapToDomainModel
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.models.FavorableBoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.mapList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber

class BoredActivityRepositoryImpl(
    private val apiClient: BoredApiClient,
    private val dbClient: BoredDbClient
):BoredActivityRepository{
    override suspend fun getNewActivity(): Flow<FavorableBoredActivity> {
        return flow<FavorableBoredActivity> {

            Timber.d("test before")
            val boredactivity = try {
            apiClient.getRandomActivity2()
        } catch (e: Exception) {
            e.printStackTrace()
        }

            Timber.d("test after, result --> result %s", boredactivity)

        }.flatMapConcat<FavorableBoredActivity, FavorableBoredActivity> {
            emptyFlow()
        }
        .flowOn(Dispatchers.IO)
//        return flow {
////            apiClient.getRandomActivity()
////                .mapToDomainModel()
////                .let {
////                    //check if activity is already saved
////                    val isSaved = isActivitySaved(it.key)
////                    kotlinx.coroutines.delay(5999)
////                    Timber.d("is saved ?? $isSaved")
////
////                    emit(FavoriteBoredActivity(isSaved, it))
////                }
//
////            apiClient.getRandomActivity2()
////                .onSuspendedSuccess {
////                    Timber.d("on success $this")
////                    Timber.d("cuurrnet thread ${Thread.currentThread()}")
////                    val activitySaved = isActivitySaved(this.key)
////                    Timber.d("is this activity saved finyally $activitySaved")
////                    emit(FavorableBoredActivity(activitySaved, this.mapToDomainModel()))
////                }
////                .onSuspendedError {
////                    Timber.d("error ${this.asError().toString()}")
////                }
//
//          //  emit(FavorableBoredActivity(true, BoredActivityDto().mapToDomainModel())
//        }.catch { throw BoredException(it) }
//        /*.flowOn(Dispatchers.IO)*/
    }
    override suspend fun saveActivity(boredActivity: BoredActivity): Flow<Unit> {
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