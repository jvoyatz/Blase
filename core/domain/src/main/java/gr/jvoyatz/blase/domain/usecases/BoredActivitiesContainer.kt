package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.AppDispatchers
import gr.jvoyatz.core.common.asResult
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Using a facade to encapsulate all use cases in it,
 * instead of injecting every single use case into the Dagger
 */
@Singleton
class BoredActivitiesContainer @Inject constructor(
    activityRepository: BoredActivityRepository,
    dispatcher: AppDispatchers
){
    val getRandomActivity: GetRandomActivityUseCase
    val getFavoriteActivities: GetFavoriteActivitiesUseCase
    val isActivitySaved: IsActivitySaved
    val saveActivity: SaveActivity
    val deleteActivity: DeleteActivityUseCase

    init {
        getRandomActivity = GetRandomActivityUseCase {
            activityRepository.getNewActivity().asResult()
        }
        deleteActivity = deleteBoredActivity(activityRepository)

        getFavoriteActivities = GetFavoriteActivitiesUseCase {
            getFavoriteActivities(activityRepository)
        }
        isActivitySaved = IsActivitySaved {
            flowOf(activityRepository.isActivitySaved(it))
                .asResult()
                .flowOn(dispatcher.io)
        }

        saveActivity = SaveActivityImpl(activityRepository)
    }
}