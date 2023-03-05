package gr.jvoyatz.blase.getactivity.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BoredActivityUiModel(
    val id: Long = 0,
    val accessibility: Double = 0.0,
    val activity: String = "",
    val key: Long,
    val link: String = "",
    val participants: Int = 0,
    val price: Double = 0.0,
    val type: String = ""
): Parcelable