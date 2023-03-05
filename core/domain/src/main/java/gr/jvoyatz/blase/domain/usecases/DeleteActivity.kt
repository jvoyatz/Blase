package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.asResult
import kotlinx.coroutines.flow.Flow

/**
 * Deletes a saved activity from the local database
 */
fun interface DeleteActivityUseCase: suspend (BoredActivity) -> Flow<ResultWrapper<Unit>>

fun deleteBoredActivity(activityRepository: BoredActivityRepository): DeleteActivityUseCase {
   return DeleteActivityUseCase {
       activityRepository.deleteActivity(it).asResult()
    }
}