package gr.jvoyatz.blase.activities.repo.datasources.network

import gr.jvoyatz.blase.activities.repo.datasources.network.adapter.ApiResponse
import gr.jvoyatz.blase.activities.repo.datasources.network.adapter.NetworkError
import gr.jvoyatz.blase.activities.repo.datasources.network.api.BoredApiService
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredErrorResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class BoredApiClientImpl
    @Inject constructor(
        private val boredApiService: BoredApiService,
): BoredApiClient {

    override suspend fun getRandomActivity(): ApiResponse<BoredActivityDto, Unit> {
        return boredApiService.getRandomActivity2()
    }

    override suspend fun getRandomActivity2(): ApiResponse<BoredActivityDto, BoredErrorResponse> {
        delay(10000)
        return safeApiCall { boredApiService.getRandomActivity() }
    }
}