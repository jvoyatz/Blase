package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.core.models.TestTest

/**
 * Fetches a new activity from the server
 *
 * Note: OOP way of implementing a use case
 */
interface GetRandomActivity {
     operator fun invoke():TestTest
}