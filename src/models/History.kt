package models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import utils.sealed.HistoryType
import utils.Serializers

@Serializable
data class History(
    val _id: @Contextual Id<History>? = null,
    @Serializable(with = Serializers.HistoryTypeSerializer::class)
    val type: HistoryType? = null,
    val text: String? = null,
    val userID: String? = null,
    val objectID: String? = null,
    val date: LocalDateTime? = null,
) : Entity