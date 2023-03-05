package gr.jvoyatz.blase.getactivity

import android.util.Log
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.blase.domain.models.FavoriteBoredActivity
import gr.jvoyatz.blase.getactivity.ui.models.BoredActivityUiModel
import gr.jvoyatz.blase.getactivity.ui.models.FavoriteBoredActivityUiModel

fun FavoriteBoredActivity.toUiModel () = FavoriteBoredActivityUiModel(
    this.isFavorite,
    this.boredActivity.toUiModel()
)

fun FavoriteBoredActivityUiModel.toDomainModel (): FavoriteBoredActivity {
    return FavoriteBoredActivity(
       this.isFavorite,
        boredActivityUiModel.toDomainModel()
    )
}


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