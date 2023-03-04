package gr.jvoyatz.blase.getactivity

/**
 * Represents user's intent for the GetNewActivity Screen
 */
sealed interface ActivitiesIntent {
    /**
     * Fetch a new activity
     */
    object GetActivity: ActivitiesIntent

    /**
     * Mark the current activity as favorite
     */
    data class FavoriteActivity(val key: String): ActivitiesIntent
}