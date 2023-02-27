package gr.jvoyatz.blase.domain.usecases

/**
 * Fetches a new activity from the server
 */
interface GetRandomActivity {
    suspend operator fun invoke()
}