package gr.jvoyatz.blase.activities.repo.datasources.network.dto

import com.squareup.moshi.Json

data class BoredActivityDto(
    @Json(name = "accessibility")
    val accessibility: Double,
    @Json(name = "activity")
    val activity: String,
    @Json(name = "key")
    val key: Long,
    @Json(name = "link")
    val link: String = "",
    @Json(name = "participants")
    val participants: Int,
    @Json(name = "price")
    val price: Double,
    @Json(name = "type")
    val type: String
)