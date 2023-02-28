package gr.jvoyatz.blase.domain.usecases

/**
 * Deletes a saved activity from the local database
 */
interface DeleteActivity {
    suspend operator fun invoke()
}

