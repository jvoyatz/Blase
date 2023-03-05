package gr.jvoyatz.blase.activities.repo.datasources.network

import gr.jvoyatz.blase.activities.repo.datasources.network.api.BoredApiService
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import javax.inject.Inject

class BoredApiClientImpl
    @Inject constructor(private val boredApiService: BoredApiService
): BoredApiClient {

    override suspend fun getRandomActivity(): BoredActivityDto {
        return boredApiService.getRandomActivity()
    }
}