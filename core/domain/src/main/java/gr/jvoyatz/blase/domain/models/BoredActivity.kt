package gr.jvoyatz.blase.domain.models


data class BoredActivity(
    val accessibility: Double = 0.0,
    val activity: String = "",
    val key: Long = 0,
    val link: String = "",
    val participants: Int = 0,
    val price: Double = 0.0,
    val type: String = ""
)