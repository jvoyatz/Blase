package gr.jvoyatz.blase.activities.network.api

import gr.jvoyatz.blase.activities.network.dto.BoredActivityDto
import retrofit2.Response
import retrofit2.http.GET

interface BoredApiService {
    @GET("activity")
    suspend fun getRandomActivity(): Response<BoredActivityDto>
}