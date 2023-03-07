package gr.jvoyatz.blase.activities.repo.datasources.network

import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredErrorResponse
import gr.jvoyatz.blase.core.network.v1.ApiResponseV2

/**
 * Wraps the execution of the request for fetching a new activity
 */
interface BoredApiClient {
    suspend fun getRandomActivity(): BoredActivityDto

    suspend fun getRandomActivity2(): ApiResponseV2<BoredActivityDto, BoredErrorResponse>
}