package gr.jvoyatz.blase.activities.repo.datasources.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BoredErrorResponse(@Json(name = "error") val error: String)