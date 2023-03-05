package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Returns the activities marked as favorite by the user
 */
fun interface GetFavoriteActivitiesUseCase: suspend () -> Flow<ResultWrapper<List<BoredActivity>>>


suspend fun getFavoriteActivities(repository: BoredActivityRepository): Flow<ResultWrapper<List<BoredActivity>>> {
    return repository.getFavoriteActivities()
        .map {
            println(it)
            val result = resultOf { it }
            println(result)
            result
        }
        .catch {
            emit(ResultWrapper.error(it))
        }
}
