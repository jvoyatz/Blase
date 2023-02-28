package gr.jvoyatz.blase.domain.usecases

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Using a facade to encapsulate all use cases in it,
 * instead of injecting every single use case into the Dagger
 */
@Singleton
class ActivitiesUseCasesFacade @Inject constructor(
    val getRandomActivity: GetRandomActivity,
    getFavoriteActivities: GetFavoriteActivities,
    isActivitySaved: IsActivitySaved,
    saveActivity: SaveActivity,
    deleteActivity: DeleteActivity
)