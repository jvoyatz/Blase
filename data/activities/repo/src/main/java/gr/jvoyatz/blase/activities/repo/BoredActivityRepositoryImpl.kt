package gr.jvoyatz.blase.activities.repo

import gr.jvoyatz.blase.activities.database.ActivityDbSource
import gr.jvoyatz.blase.activities.network.ActivityNetworkSource
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.blase.logging.LogEvent

class BoredActivityRepositoryImpl(
    private val apiSource: ActivityNetworkSource,
    private val dbSource: ActivityDbSource
):BoredActivityRepository{
    override suspend fun getNewActivity() {
        LogEvent.d("fetching new activity")
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