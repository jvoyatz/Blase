package gr.jvoyatz.blase.getactivity

import android.os.Parcelable
import gr.jvoyatz.blase.domain.models.BoredActivity
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class GetActivityUiState(
    val isLoading: Boolean = false,
    val newActivity: @RawValue BoredActivity? = null,
    val isError: Boolean = false
):Parcelable {
    sealed interface InternalUiState{
        object Loading: InternalUiState

        data class OnNewActivityFetched(val boredActivity: BoredActivity): InternalUiState

        data class Error(val throwable: Throwable): InternalUiState
    }
}