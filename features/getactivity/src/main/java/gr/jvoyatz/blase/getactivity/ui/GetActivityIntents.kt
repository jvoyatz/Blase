package gr.jvoyatz.blase.getactivity.ui

import gr.jvoyatz.blase.getactivity.ui.models.BoredActivityUiModel

/**
 * Represents user's intent for the GetNewActivity Screen
 */
sealed interface GetActivityIntents {
    /**
     * Fetch a new activity
     */
    object FetchActivity: GetActivityIntents

    /**
     * Mark the current activity as favorite
     */
    data class FavoriteActivity(val activity: BoredActivityUiModel): GetActivityIntents
}

