package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.asResult
import kotlinx.coroutines.flow.Flow

internal class SaveActivityImpl(
    private val repository: BoredActivityRepository
): SaveActivity {
    override suspend fun invoke(activity: BoredActivity): Flow<ResultWrapper<Unit>> {
        return repository.saveActivity(activity).asResult()
    }
}
