package models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import utils.Serializers
import utils.sealed.EntityType
import utils.sealed.HistoryType

@Serializable
data class History(
    val _id: @Contextual Id<History>? = null,
    @Serializable(with = Serializers.HistoryTypeSerializer::class)
    val historyType: HistoryType? = null,
    @Serializable(with = Serializers.EntityTypeSerializer::class)
    val entityType: EntityType? = null,
    val text: String? = null,
    val userID: String? = null,
    val objectID: String? = null,
    val date: LocalDateTime? = null,
) : Entity