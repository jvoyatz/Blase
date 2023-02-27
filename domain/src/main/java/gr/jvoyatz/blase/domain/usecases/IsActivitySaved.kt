package gr.jvoyatz.blase.domain.usecases

/**
 * Checks whether an activity is already stored in the database
 */
interface IsActivitySaved {
    suspend operator fun invoke(key: String)
}