package gr.jvoyatz.blase.activities.repo.mapper

import gr.jvoyatz.blase.database.entities.BoredActivityEntity
import gr.jvoyatz.blase.domain.models.BoredActivity
import gr.jvoyatz.core.common.Mapper

object BoredEntityMapper : Mapper<BoredActivityEntity, BoredActivity> {

    override fun BoredActivityEntity.mapToDomainModel() = BoredActivity(
        accessibility = this.accessibility,
        activity = this.activity,
        key = this.key,
        link = this.link,
        participants = this.participants,
        price = this.price,
        type = this.type
    )

    override fun BoredActivity.mapFromDomainModel(): BoredActivityEntity {
        return BoredActivityEntity(
            key,
            accessibility,
            activity,
            link,
            participants,
            price,
            type
        )
    }
}