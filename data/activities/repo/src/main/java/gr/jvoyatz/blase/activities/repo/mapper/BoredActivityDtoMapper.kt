package gr.jvoyatz.blase.activities.repo.mapper

import gr.jvoyatz.blase.activities.repo.datasources.network.dto.BoredActivityDto
import gr.jvoyatz.blase.domain.models.BoredActivity

object BoredActivityDtoMapper : Mapper<BoredActivityDto, BoredActivity> {

    override fun BoredActivityDto.mapToDomainModel() = BoredActivity(
        accessibility = this.accessibility,
        activity = this.activity,
        key = this.key,
        link = this.link,
        participants = this.participants,
        price = this.price,
        type = this.type
    )

    override fun BoredActivity.mapFromDomainModel(): BoredActivityDto {
        return BoredActivityDto(
            accessibility,
            activity,
            key,
            link,
            participants,
            price,
            type
        )
    }
}