package gr.jvoyatz.blase.getactivity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.usecases.BoredActivitiesContainer
import gr.jvoyatz.blase.domain.usecases.GetRandomActivityUseCase
import gr.jvoyatz.blase.getactivity.ui.GetActivityIntents
import gr.jvoyatz.blase.getactivity.ui.state.GetActivityUiState
import gr.jvoyatz.blase.getactivity.ui.state.GetActivityUiState.InternalUiState
import gr.jvoyatz.core.common.onError
import gr.jvoyatz.core.common.onSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val SAVED_GET_ACTIVITY_UI_STATE = "savedActivityUiState"

@HiltViewModel
class GetActivityViewModel @Inject constructor(
    val useCasesFacade: BoredActivitiesContainer,
    initUiState: GetActivityUiState,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val userIntentFlow = MutableSharedFlow<GetActivityIntents>()
    private val getRandomActivity: GetRandomActivityUseCase = useCasesFacade.getRandomActivity
    val uiState = savedStateHandle.getStateFlow(SAVED_GET_ACTIVITY_UI_STATE, initUiState)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.d("Handle $exception in CoroutineExceptionHandler")
    }
    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            userIntentFlow.flatMapMerge {
                mapIntentToUseCase(it)
            }
            .runningFold(uiState.value, ::mapInternalUiStateToExternal)
            .catch {
                it.printStackTrace()
            }
            .collect {
                savedStateHandle[SAVED_GET_ACTIVITY_UI_STATE] = it
            }

        }

        viewModelScope.launch {
            delay(10000)
            Timber.d("is fasdfadf ")
        }
        viewModelScope.launch {
            useCasesFacade.getFavoriteActivities().collect{
                Timber.d("collected favorite activities ${it}")
                it.onSuccess { list ->
                    for (act: BoredActivity in list){
//                        if(act.key.toInt() % 2 == 0){
//                            useCasesFacade.isActivitySaved(act.key + 999234)
//                                .collect{
//                                    Timber.w("is saved $it")
//                                }
//                        }
//                        useCasesFacade.isActivitySaved(act.key)
//                            .collect{
//                                Timber.w("is saved $it")
//                            }
                        if(act.key.toInt() % 2 == 0) {
                            Timber.d("attempting to delete ${act.key}")
                            useCasesFacade.deleteActivity(act)
                                .collect{
                                    it.onSuccess {
                                        Timber.w("delete ok")
                                    }
                                    .onError {
                                        Timber.e(it)
                                    }
                                }

                        }
                    }
                }
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
            onUserIntent(GetActivityIntents.FetchActivity)
        }
    }

    /**
     * Receives the intent of the user and maps it to the corresponding
     * use case.
     */
    private fun mapIntentToUseCase(intent: GetActivityIntents): Flow<InternalUiState>{
        return when(intent){
            is GetActivityIntents.FetchActivity -> getNewActivity()
            is GetActivityIntents.FavoriteActivity -> flowOf(intent.activity)
                .map { it.toDomainModel() }
                .flowOn(Dispatchers.Default)
                .flatMapMerge {
                    saveActivity(it)
                }

            //do nothing here for now
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
    fun onUserIntent(intent: GetActivityIntents){
        viewModelScope.launch {
            userIntentFlow.emit(intent)
        }
    }

    //use cases invocation
    private fun saveActivity(activity: BoredActivity): Flow<InternalUiState> = flow<InternalUiState> {
        useCasesFacade.saveActivity(activity)
            .collect {
                it.onSuccess {
                    Timber.i("activity saved!!")
                }
                .onError { throwable ->
                    Timber.e(throwable,"activity not saved!!")
                }
            }

        }.catch { emit(InternalUiState.Error(it)) }
        .flowOn(Dispatchers.Default)

    /**
     * Attempts to fetch a new activity, and after parsing the returned object
     * emits the new (internal) ui state for this intent.
     */
    private fun getNewActivity(): Flow<InternalUiState> = flow {
        getRandomActivity()
            .onStart { emit(InternalUiState.Loading) }
            .collect { result ->
                result
                    .onSuccess { emit(InternalUiState.OnNewActivityFetched(it)) }
                    .onError { emit(InternalUiState.Error(it)) }
            }
    }.flowOn(Dispatchers.Default)

}