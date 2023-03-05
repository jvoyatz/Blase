package gr.jvoyatz.blase.getactivity

import android.util.Log
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.logging.LogEvent


fun BoredActivity.toUiModel () =
    BoredActivityUiModel(
        accessibility = this.accessibility,
        activity = this.activity,
        key = this.key,
        link = this.link,
        participants = this.participants,
        price = this.price,
        type = this.type
    )

fun BoredActivityUiModel.toDomainModel (): BoredActivity {
    Log.d(TAG, "toDomainModel: " + Thread.currentThread())
   return BoredActivity(
        accessibility = this.accessibility,
        activity = this.activity,
        key = this.key,
        link = this.link,
        participants = this.participants,
        price = this.price,
        type = this.type
    )

}

private const val TAG = "BoredActivityMapper"