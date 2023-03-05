package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Deletes a saved activity from the local database
 */
fun interface DeleteActivityUseCase: suspend (BoredActivity) -> Flow<ResultWrapper<Unit>>

fun deleteBoredActivity(activityRepository: BoredActivityRepository): DeleteActivityUseCase {
   return DeleteActivityUseCase {
        activityRepository.deleteActivity(it)
            .map {
                resultOf { it }
            }
            .catch {
                emit(ResultWrapper.error(it))
            }
    }
}