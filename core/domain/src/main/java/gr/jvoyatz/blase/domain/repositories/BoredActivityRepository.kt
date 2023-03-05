package gr.jvoyatz.blase.domain.repositories

import gr.jvoyatz.blase.domain.models.BoredActivity
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the set of methods for
 * interacting with bored api remote service as well as
 * storing/deleting them locally.
 */
interface BoredActivityRepository {

    /**
     * Fetches a new activity from the remote service
     */
    suspend fun getNewActivity(): Flow<BoredActivity>

    /**
     * Saves a fetched activity into the local database
     */
    suspend fun saveActivity(boredActivity: BoredActivity): Flow<Unit>

    /**
     * Deletes an already stored activity
     */
    suspend fun deleteActivity(key: String)

    /**
     * Checks whether the activity with this key
     * is already saved
     */
    suspend fun isActivitySaved(key: String)

    /**
     * Returns a list of the favorite activities
     */
    suspend fun getFavoriteActivities(): Flow<List<BoredActivity>>
}