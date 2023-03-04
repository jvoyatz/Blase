package gr.jvoyatz.blase.activities.repo.mapper

interface Mapper<From, To> {
    fun From.mapToDomainModel(): To
    fun To.mapFromDomainModel(): From
}