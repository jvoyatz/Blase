package gr.jvoyatz.blase.activities.repo.datasources.local

import androidx.room.*
import gr.jvoyatz.blase.activities.repo.datasources.local.entities.BoredActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoredActivityDao {
    @Query("SELECT * FROM BoredActivityEntity")
    fun getActivities(): Flow<List<BoredActivityEntity>>

    @Insert
    fun saveActivity(activityEntity: BoredActivityEntity)

    @Delete
    fun deleteActivity(activityEntity: BoredActivityEntity)

    @Query("SELECT EXISTS(SELECT * FROM BoredActivityEntity WHERE `key` = :key)")
    fun isActivitySaved(key: Long): Boolean
}