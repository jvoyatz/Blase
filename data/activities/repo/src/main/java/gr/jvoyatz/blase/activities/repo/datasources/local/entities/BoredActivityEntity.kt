package gr.jvoyatz.blase.activities.repo.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BoredActivityEntity(
    @PrimaryKey
    val key: Long,
    val accessibility: Double,
    val activity: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
)