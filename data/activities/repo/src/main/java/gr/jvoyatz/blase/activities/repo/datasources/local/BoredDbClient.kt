package gr.jvoyatz.blase.activities.repo.datasources.local

import gr.jvoyatz.blase.database.entities.BoredActivityEntity
import kotlinx.coroutines.flow.Flow

interface BoredDbClient {
    suspend fun saveActivity(activityEntity: BoredActivityEntity): Flow<Unit>

    suspend fun deleteActivity(activityEntity: BoredActivityEntity)

    suspend fun isActivitySaved(key: Long): Boolean

    suspend fun getFavoriteActivities(): Flow<List<BoredActivityEntity>>
}