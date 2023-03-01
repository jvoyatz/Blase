package gr.jvoyatz.blase.activities.repo

import gr.jvoyatz.blase.activities.network.BoredApiClient
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.blase.logging.LogEvent

class BoredActivityRepositoryImpl(
    private val apiClient: BoredApiClient,
    //private val dbSource: ActivityDbSource
):BoredActivityRepository{
    override suspend fun getNewActivity() {
        LogEvent.d("fetching new activity, thread is ${Thread.currentThread()}")

        println(apiClient.getRandomActivity())
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