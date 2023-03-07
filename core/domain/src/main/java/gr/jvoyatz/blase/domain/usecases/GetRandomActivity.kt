package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.FavorableBoredActivity
import gr.jvoyatz.core.common.ResultWrapper
import kotlinx.coroutines.flow.Flow

/**
 * Fetches a new activity from the server
 *
 * Note: OOP way of implementing a use case
 */
fun interface GetRandomActivityUseCase: suspend () -> Flow<ResultWrapper<FavorableBoredActivity>>