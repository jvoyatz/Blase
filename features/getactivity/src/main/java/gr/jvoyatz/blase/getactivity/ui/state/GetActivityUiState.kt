package gr.jvoyatz.blase.getactivity.ui.state

import android.os.Parcelable
import gr.jvoyatz.blase.domain.models.FavorableBoredActivity
import gr.jvoyatz.blase.getactivity.ui.models.FavoriteBoredActivityUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetActivityUiState(
    val isLoading: Boolean = false,
    val newActivity: FavoriteBoredActivityUiModel? = null,
    val isError: Boolean = false
):Parcelable {
    sealed interface InternalUiState{
        object Loading: InternalUiState

        data class OnNewActivityFetched(val boredActivity: FavorableBoredActivity): InternalUiState

        data class Error(val throwable: Throwable): InternalUiState
    }
}


fun GetActivityUiState.getOrNull(): FavoriteBoredActivityUiModel? = newActivity