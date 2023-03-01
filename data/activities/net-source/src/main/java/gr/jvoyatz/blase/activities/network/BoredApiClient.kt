package gr.jvoyatz.blase.activities.network

import gr.jvoyatz.blase.activities.network.dto.BoredActivityDto
import gr.jvoyatz.blase.core.network.v1.ApiResponse

/**
 * Wraps the execution of the request for fetching a new activity
 */
interface BoredApiClient {
    suspend fun getRandomActivity(): ApiResponse<BoredActivityDto>
}