package gr.jvoyatz.blase.activities.repo.mapper

interface Mapper<From, To> {
    fun From.mapToDomainModel(): To
    fun To.mapFromDomainModel(): From
}


//inline fun <I, O> mapList(input: List<I>, mapSingle: (I) -> O): List<O> {
//    return input.map { mapSingle(it) }
//}
inline fun <I, O> List<I>.mapList(mapSingle: (I) -> O): List<O> {
    return this.map { mapSingle(it) }
}
inline fun <I, O> mapNullInputList(input: List<I>?, mapSingle: (I) -> O): List<O> {
    return input?.map { mapSingle(it) } ?: emptyList()
}