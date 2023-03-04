package gr.jvoyatz.blase.getactivity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.jvoyatz.blase.domain.usecases.ActivitiesUseCasesFacade
import gr.jvoyatz.blase.domain.usecases.GetRandomActivityUseCase
import gr.jvoyatz.blase.getactivity.GetActivityUiState.InternalUiState
import gr.jvoyatz.blase.logging.LogEvent
import gr.jvoyatz.core.common.onError
import gr.jvoyatz.core.common.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVED_GET_ACTIVITY_UI_STATE = "savedActivityUiState"

@HiltViewModel
class GetActivityViewModel @Inject constructor(
    useCasesFacade: ActivitiesUseCasesFacade,
    initUiState: GetActivityUiState,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val userIntentFlow = MutableSharedFlow<ActivitiesIntent>()
    private val getRandomActivity: GetRandomActivityUseCase = useCasesFacade.getRandomActivity
    val uiState = savedStateHandle.getStateFlow(SAVED_GET_ACTIVITY_UI_STATE, initUiState)

    init {
        viewModelScope.launch {
            userIntentFlow.flatMapMerge {
                mapIntentToUseCase(it)
            }
            .scan(uiState.value, ::mapInternalUiStateToExternal)
            .catch { }
            .collect {
                savedStateHandle[SAVED_GET_ACTIVITY_UI_STATE] = it
            }
        }
//        viewModelScope.launch {
//            getRandomActivity()
//                .catch {  LogEvent.d("error occurred $it") }
//                .collect {
//                    savedStateHandle[SAVED_GET_ACTIVITY_UI_STATE] = when(it) {
//                        is ResultWrapper.Success -> {
//                            GetActivityUiState(newActivity = it.data)
//                        }
//                        is ResultWrapper.Error -> {
//                            GetActivityUiState(isError = true)
//                        }
//                        else -> {
//                            GetActivityUiState(isLoading = true)
//                        }
//                    }
//                }
//        }
    }

    init {
        viewModelScope.launch {
            delay(250)
            onUserIntent(ActivitiesIntent.GetActivity)
        }
    }

    /**
     * Attempts to fetch a new activity, and after parsing the returned object
     * emits the new (internal) ui state for this intent.
     */
    private fun getNewActivity(): Flow<InternalUiState> = flow {
        getRandomActivity()
            .onStart {
                LogEvent.d("onstart called")
                emit(InternalUiState.Loading)
            }
            .collect {
                LogEvent.logThread()
                it.onSuccess { activity ->
                    emit(InternalUiState.OnNewActivityFetched(activity))
                }
                .onError { _e ->
                    emit(InternalUiState.Error(_e))
                }
            }
    }.flowOn(Dispatchers.Default)

    /**
     * Receives the intent of the user and maps it to the corresponding
     * use case.
     */
    private fun mapIntentToUseCase(intent: ActivitiesIntent): Flow<InternalUiState>{
        return when(intent){
            is ActivitiesIntent.GetActivity -> getNewActivity()
            else -> emptyFlow() //do nothing here for now
        }
    }

    /**
     * Parses the result received for the user's intent and maps it,
     * into a new GetActivityUiState
     */
    private fun mapInternalUiStateToExternal(currentUiState: GetActivityUiState, internalUiState: InternalUiState):GetActivityUiState {
       return when (internalUiState) {
            is InternalUiState.OnNewActivityFetched -> {
                currentUiState.copy(
                    isLoading = false,
                    isError = false,
                    newActivity = internalUiState.boredActivity.toUiModel()
                )
            }
            is InternalUiState.Error -> {
                currentUiState.copy(
                    isLoading = false,
                    isError = true,
                    newActivity = null
                )
            }
            is InternalUiState.Loading -> {
                currentUiState.copy(
                    isLoading = true,
                    isError = false,
                    newActivity = null
                )
            }
        }
    }

    /**
     * Receives an intent which represents a user action
     */
    fun onUserIntent(intent: ActivitiesIntent){
        viewModelScope.launch {
            userIntentFlow.emit(intent)
        }
    }
}
