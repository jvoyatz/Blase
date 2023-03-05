package gr.jvoyatz.blase.activities.repo.datasources.local

import gr.jvoyatz.blase.activities.repo.datasources.local.entities.BoredActivityEntity
import gr.jvoyatz.blase.logging.LogEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


internal class BoredDbClientImpl(
    private val dao: BoredActivityDao
): BoredDbClient {
    override suspend fun saveActivity(activityEntity: BoredActivityEntity): Flow<Unit> {
         return flow<Unit> {
            dao.saveActivity(activityEntity)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteActivity(activityEntity: BoredActivityEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun isActivitySaved(key: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteActivities(): Flow<List<BoredActivityEntity>> {
        return dao.getActivities()
    }
}