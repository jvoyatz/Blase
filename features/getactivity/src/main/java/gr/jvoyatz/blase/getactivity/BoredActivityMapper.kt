package gr.jvoyatz.blase.getactivity

import gr.jvoyatz.blase.domain.models.BoredActivity


fun BoredActivity.toUiModel () =
    BoredActivityUiModel(
        id = this.id,
        accessibility = this.accessibility,
        activity = this.activity,
        key = this.key,
        link = this.link,
        participants = this.participants,
        price = this.price,
        type = this.type
    )