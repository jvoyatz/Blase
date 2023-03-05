package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.core.common.ResultWrapper
import kotlinx.coroutines.flow.Flow

/**
 * Checks whether an activity is already stored in the database
 */
fun interface IsActivitySaved:suspend (Long) -> Flow<ResultWrapper<Boolean>>