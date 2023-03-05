package gr.jvoyatz.blase.database

import androidx.room.*
import gr.jvoyatz.blase.database.entities.BoredActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoredActivityDao {
    @Query("SELECT * FROM BoredActivityEntity")
    fun getActivities(): Flow<List<BoredActivityEntity>>

    @Insert
    suspend fun saveActivity(activityEntity: BoredActivityEntity)

    @Delete
    suspend fun deleteActivity(activityEntity: BoredActivityEntity)

    @Query("SELECT EXISTS(SELECT * FROM BoredActivityEntity WHERE `key` = :key)")
    suspend fun isActivitySaved(key: Long): Boolean
}