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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVED_GET_ACTIVITY_UI_STATE = "savedActivityUiState"

@HiltViewModel
class GetActivityViewModel @Inject constructor(
    private val useCasesFacade: ActivitiesUseCasesFacade,
    private val initUiState: GetActivityUiState,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val userIntentFlow = MutableSharedFlow<ActivitiesIntent>()

    private val getRandomActivity: GetRandomActivityUseCase = useCasesFacade.getRandomActivity
    val uiState = savedStateHandle.getStateFlow(SAVED_GET_ACTIVITY_UI_STATE, initUiState)

    init {
        LogEvent.d("init block 1, setting flow observation")
        viewModelScope.launch {
            userIntentFlow.flatMapMerge {
                LogEvent.i("received a new intent --> [$it]")
                executeIntent(it)
            }
            .runningFold(uiState.value, ::foldUiState)
            .catch { LogEvent.e(it, "an exception occurred!!") }
            .collect {
                LogEvent.i("collected the new ui state --> $it")
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
        LogEvent.d("init block 2, init intent")
        onUserIntent(ActivitiesIntent.GetActivity)
    }
    /**
     * Attempts to fetch a new activity, and after parsing the returned object
     * emits the new (internal) ui state for this intent.
     */
    private fun getNewActivity(): Flow<InternalUiState> = flow {
        getRandomActivity().onStart { emit(InternalUiState.Loading) }
            .collect {
                it.onSuccess { activity ->
                    emit(InternalUiState.OnNewActivityFetched(activity))
                }
                .onError { _e ->
                    emit(InternalUiState.Error(_e))
                }
            }
    }

    /**
     * Receives the intent of the user and maps it to the corresponding
     * use case.
     */
    private fun executeIntent(intent: ActivitiesIntent): Flow<InternalUiState>{
        return when(intent){
            is ActivitiesIntent.GetActivity -> getNewActivity()
            else -> emptyFlow() //do nothing here for now
        }
    }

    /**
     * Parses the result received for the user's intent and maps it,
     * into a new GetActivityUiState
     */
    private fun foldUiState(currentUiState: GetActivityUiState, internalUiState: InternalUiState) =
        when(internalUiState){
            is InternalUiState.OnNewActivityFetched -> {
                currentUiState.copy(
                    isLoading = false,
                    isError = false,
                    newActivity = internalUiState.boredActivity
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

    fun onUserIntent(intent: ActivitiesIntent){
        viewModelScope.launch {
            userIntentFlow.emit(intent)
        }
    }
}