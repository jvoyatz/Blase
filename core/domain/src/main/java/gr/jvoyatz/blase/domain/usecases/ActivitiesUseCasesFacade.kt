package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
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
    val getFavoriteActivities: GetFavoriteActivities
    val isActivitySaved: IsActivitySaved
    val saveActivity: SaveActivity
    val deleteActivity: DeleteActivity
    init {
        getRandomActivity = GetRandomActivityUseCase {
            getRandomActivity(activityRepository)
        }
        deleteActivity = DeleteActivity(activityRepository::deleteActivity)
        getFavoriteActivities = GetFavoriteActivities {
            activityRepository.getFavoriteActivities()
        }
        isActivitySaved = IsActivitySaved {
            activityRepository.isActivitySaved(it)
        }
        saveActivity = SaveActivity(activityRepository::saveActivity)
    }
}