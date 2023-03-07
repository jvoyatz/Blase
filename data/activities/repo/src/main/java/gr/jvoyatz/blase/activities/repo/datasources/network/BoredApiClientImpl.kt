package gr.jvoyatz.blase.activities.repo.datasources.network

import gr.jvoyatz.blase.activities.repo.datasources.network.api.BoredApiService
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredErrorResponse
import gr.jvoyatz.blase.core.network.v1.ApiResponseV2
import gr.jvoyatz.blase.core.network.v1.utils.RetrofitErrorResponseMapper
import gr.jvoyatz.blase.core.network.v1.utils.safeApiCall
import javax.inject.Inject

class BoredApiClientImpl
    @Inject constructor(
        private val boredApiService: BoredApiService,
        private val retrofitErrorResponseMapper: RetrofitErrorResponseMapper
): BoredApiClient {

    override suspend fun getRandomActivity(): BoredActivityDto {
        return boredApiService.getRandomActivity()
    }

    override suspend fun getRandomActivity2(): ApiResponseV2<BoredActivityDto, BoredErrorResponse> {
        return safeApiCall ({ boredApiService.getRandomActivity() }, retrofitErrorResponseMapper)
    }
}