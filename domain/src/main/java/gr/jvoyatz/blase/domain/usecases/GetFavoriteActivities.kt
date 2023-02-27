package gr.jvoyatz.blase.domain.usecases

/**
 * Returns the activities marked as favorite by the user
 */
interface GetFavoriteActivities {
    suspend operator fun invoke()
}