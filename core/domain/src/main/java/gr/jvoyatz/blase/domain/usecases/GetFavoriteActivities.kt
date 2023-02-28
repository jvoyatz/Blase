package gr.jvoyatz.blase.domain.usecases

/**
 * Returns the activities marked as favorite by the user
 */
fun interface GetFavoriteActivities: suspend () -> Unit
