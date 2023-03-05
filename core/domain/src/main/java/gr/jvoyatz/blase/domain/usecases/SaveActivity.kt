package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.repositories.BoredActivityRepository
import gr.jvoyatz.core.common.ResultWrapper
import gr.jvoyatz.core.common.resultOf
import kotlinx.coroutines.flow.Flow

/**
 * Attemps to store an activity in the local database
 */
//fun interface SaveActivity: suspend (BoredActivity) -> Unit

interface SaveActivity{
    suspend operator fun invoke(activity: BoredActivity): Flow<ResultWrapper<Unit>>
}

//suspend fun saveBoredActivity(activityRepository: BoredActivityRepository): ResultWrapper<SaveActivity> {
//    val func: SaveActivity =  object: suspend (BoredActivity) -> Unit, SaveActivity {
//        override suspend fun invoke(boredActivity: BoredActivity) {
//            println("anonymous func called! with activity $boredActivity")
//            activityRepository.saveActivity(boredActivity)
//        }
//    }
//    return
//}