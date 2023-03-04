package gr.jvoyatz.blase.activities.repo.datasources.network.api

import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import retrofit2.Response
import retrofit2.http.GET

interface BoredApiService {
    @GET("activity")
    suspend fun getRandomActivity(): Response<BoredActivityDto>

    suspend fun getRandomActivity2(): BoredActivityDto
}