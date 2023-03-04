package gr.jvoyatz.blase.domain.models


data class BoredActivity(
    val id: Long = 0,
    val accessibility: Double = 0.0,
    val activity: String = "",
    val key: String = "",
    val link: String = "",
    val participants: Int = 0,
    val price: Double = 0.0,
    val type: String = ""
)