package gr.jvoyatz.blase.activities.repo.datasources.network

import gr.jvoyatz.blase.activities.repo.datasources.network.api.BoredApiService
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import gr.jvoyatz.blase.core.network.v1.ApiResponse
import gr.jvoyatz.blase.core.network.v1.safeApiCall
import gr.jvoyatz.blase.core.network.v1.safeSuspendableApiCall
import javax.inject.Inject

class BoredApiClientImpl
    @Inject constructor(private val boredApiService: BoredApiService
): BoredApiClient {

    override suspend fun getRandomActivity(): BoredActivityDto {
        return boredApiService.getRandomActivity()
    }
}