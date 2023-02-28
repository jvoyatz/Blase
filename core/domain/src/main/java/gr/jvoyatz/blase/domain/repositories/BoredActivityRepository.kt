package gr.jvoyatz.blase.domain.repositories

/**
 * Interface defining the set of methods for
 * interacting with bored api remote service as well as
 * storing/deleting them locally.
 */
interface BoredActivityRepository {

    /**
     * Fetches a new activity from the remote service
     */
    suspend fun getNewActivity()

    /**
     * Saves a fetched activity into the local database
     */
    suspend fun saveActivity()

    /**
     * Deletes an already stored activity
     */
    suspend fun deleteActivity()

    /**
     * Checks whether the activity with this key
     * is already saved
     */
    suspend fun isActivitySaved(key: String)

    /**
     * Returns a list of the favorite activities
     */
    suspend fun getFavoriteActivities()
}