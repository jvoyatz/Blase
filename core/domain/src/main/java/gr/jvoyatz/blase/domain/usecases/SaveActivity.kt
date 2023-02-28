package gr.jvoyatz.blase.domain.usecases

/**
 * Attemps to store an activity in the local database
 */
fun interface SaveActivity: suspend () -> Unit