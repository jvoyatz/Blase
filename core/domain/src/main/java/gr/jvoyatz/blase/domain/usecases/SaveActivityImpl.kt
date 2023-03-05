package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SaveActivityImpl(
    private val repository: BoredActivityRepository
): SaveActivity {
    override suspend fun invoke(activity: BoredActivity): Flow<ResultWrapper<Unit>> {
        return repository.saveActivity(activity)
            .map {
                resultOf { it }
            }

    }
}
