package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.asResult
import kotlinx.coroutines.flow.Flow

/**
 * Returns the activities marked as favorite by the user
 */
fun interface GetFavoriteActivitiesUseCase: suspend () -> Flow<ResultWrapper<List<BoredActivity>>>


suspend fun getFavoriteActivities(repository: BoredActivityRepository) =
    repository.getFavoriteActivities().asResult()

