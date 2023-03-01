package gr.jvoyatz.blase.activities.repo

import gr.jvoyatz.blase.activities.database.ActivityDbSource
import gr.jvoyatz.blase.activities.network.ActivityNetworkSource
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository

class BoredActivityRepositoryImpl(
    private val apiSource: ActivityNetworkSource,
    private val dbSource: ActivityDbSource
):BoredActivityRepository{
    override suspend fun getNewActivity() {
        TODO("Not yet implemented")
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