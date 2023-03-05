package gr.jvoyatz.blase.activities.repo.datasources.network.dto

import com.squareup.moshi.Json

data class BoredActivityDto(
    @Json(name = "accessibility")
    val accessibility: Double = 0.0,
    @Json(name = "activity")
    val activity: String = "",
    @Json(name = "key")
    val key: Long = 0,
    @Json(name = "link")
    val link: String = "",
    @Json(name = "participants")
    val participants: Int = 0,
    @Json(name = "price")
    val price: Double = 0.0,
    @Json(name = "type")
    val type: String = ""
)