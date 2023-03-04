package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.resultOf
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Fetches a new activity from the server
 *
 * Note: OOP way of implementing a use case
 */
fun interface GetRandomActivityUseCase: suspend () -> Flow<ResultWrapper<BoredActivity>>


suspend fun getRandomActivity(repository: BoredActivityRepository): Flow<ResultWrapper<BoredActivity>> {
    return repository.getNewActivity()
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
