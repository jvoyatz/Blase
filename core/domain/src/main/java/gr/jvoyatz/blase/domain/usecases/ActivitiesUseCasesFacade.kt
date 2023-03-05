package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.resultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Using a facade to encapsulate all use cases in it,
 * instead of injecting every single use case into the Dagger
 */
@Singleton
class ActivitiesUseCasesFacade @Inject constructor(
    activityRepository: BoredActivityRepository
){
    val getRandomActivity: GetRandomActivityUseCase
    val getFavoriteActivities: GetFavoriteActivitiesUseCase
    val isActivitySaved: IsActivitySaved
    val saveActivity: SaveActivity
    val deleteActivity: DeleteActivityUseCase
    init {
        getRandomActivity = GetRandomActivityUseCase {
            getRandomActivity(activityRepository)
        }
        deleteActivity = deleteBoredActivity(activityRepository)

        getFavoriteActivities = GetFavoriteActivitiesUseCase {
            getFavoriteActivities(activityRepository)
        }
        isActivitySaved = IsActivitySaved {
            flow<ResultWrapper<Boolean>> {
                resultOf {
                    emit(ResultWrapper.success(activityRepository.isActivitySaved(it)))
                }
            }
            .catch { emit(ResultWrapper.error(it)) }
            .flowOn(Dispatchers.Default)
        }

        saveActivity = SaveActivityImpl(activityRepository)
    }
}