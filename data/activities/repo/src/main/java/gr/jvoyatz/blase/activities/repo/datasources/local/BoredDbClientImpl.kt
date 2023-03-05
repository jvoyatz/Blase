package gr.jvoyatz.blase.activities.repo.datasources.local

import gr.jvoyatz.blase.database.BoredActivityDao
import gr.jvoyatz.blase.database.entities.BoredActivityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


internal class BoredDbClientImpl(
    private val dao: BoredActivityDao
): BoredDbClient {
    override suspend fun saveActivity(activityEntity: BoredActivityEntity) = flow<Unit> {
            dao.saveActivity(activityEntity)
        }.flowOn(Dispatchers.IO)

    override suspend fun deleteActivity(activityEntity: BoredActivityEntity) {
        dao.deleteActivity(activityEntity)
    }

    override suspend fun isActivitySaved(key: Long) = dao.isActivitySaved(key)

    override suspend fun getFavoriteActivities() = dao.getActivities()
}