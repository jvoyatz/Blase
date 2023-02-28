package gr.jvoyatz.blase.domain.usecases

/**
 * Deletes a saved activity from the local database
 */
fun interface DeleteActivity: suspend (String) -> Unit

