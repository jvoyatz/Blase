package gr.jvoyatz.blase.activities.repo.datasources.network.api

import gr.jvoyatz.blase.core.network.v1.config.ApiResponse
import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import retrofit2.Response
import retrofit2.http.GET

interface BoredApiService {

    @GET("activity2")
    suspend fun getRandomActivity2(): ApiResponse<BoredActivityDto, Unit>

    @GET("activity1")
    suspend fun getRandomActivity(): Response<BoredActivityDto>
}