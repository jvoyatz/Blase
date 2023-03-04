package gr.jvoyatz.blase.activities.repo.datasources.network

import gr.jvoyatz.blase.activities.repo.datasources.network.api.BoredApiService
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import gr.jvoyatz.blase.core.network.v1.ApiResponse
import gr.jvoyatz.blase.core.network.v1.safeApiCall

internal class BoredApiClientImpl(
    private val boredApiService: BoredApiService
): BoredApiClient {

    override suspend fun getRandomActivity(): ApiResponse<BoredActivityDto> {
        return safeApiCall { boredApiService.getRandomActivity() }
    }
}