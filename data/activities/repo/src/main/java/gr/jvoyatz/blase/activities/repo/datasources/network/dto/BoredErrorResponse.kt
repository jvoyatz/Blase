package gr.jvoyatz.blase.activities.repo.datasources.network.dto

import com.squareup.moshi.Json

data class BoredErrorResponse(@Json(name = "error") val error: String)