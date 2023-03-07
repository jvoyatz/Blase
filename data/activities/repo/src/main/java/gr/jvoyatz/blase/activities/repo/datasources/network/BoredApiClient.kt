package gr.jvoyatz.blase.activities.repo.datasources.network

import gr.jvoyatz.blase.core.network.v1.config.ApiResponse
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredErrorResponse

/**
 * Wraps the execution of the request for fetching a new activity
 */
interface BoredApiClient {
    suspend fun getRandomActivity(): ApiResponse<BoredActivityDto, Unit>

    suspend fun getRandomActivity2(): ApiResponse<BoredActivityDto, BoredErrorResponse>
}