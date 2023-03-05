package gr.jvoyatz.blase.getactivity.ui.models

import android.os.Parcelable
import gr.jvoyatz.blase.getactivity.ui.models.BoredActivityUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteBoredActivityUiModel(
    val isFavorite: Boolean,
    val boredActivityUiModel: BoredActivityUiModel
): Parcelable