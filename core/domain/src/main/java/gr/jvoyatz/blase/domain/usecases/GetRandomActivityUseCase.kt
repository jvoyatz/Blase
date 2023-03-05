package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.FavoriteBoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Fetches a new activity from the server
 *
 * Note: OOP way of implementing a use case
 */
fun interface GetRandomActivityUseCase: suspend () -> Flow<ResultWrapper<FavoriteBoredActivity>>


suspend fun getRandomActivity(repository: BoredActivityRepository): Flow<ResultWrapper<FavoriteBoredActivity>> {
    return repository.getNewActivity()
        .map {
            resultOf { it }
        }
        .catch {
            emit(ResultWrapper.error(it))
        }
}
