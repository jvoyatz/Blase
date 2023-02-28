package gr.jvoyatz.blase.domain.usecases

/**
 * Checks whether an activity is already stored in the database
 */
fun interface IsActivitySaved:suspend (String) -> Unit