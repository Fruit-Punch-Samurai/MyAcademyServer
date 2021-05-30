package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import utils.RankType
import utils.Serializers

@Serializable
data class PrivateUser(
    val _id: @Contextual Id<PrivateUser>? = null,
    val name: String? = null,
    val password: String? = null,
    @Serializable(with = Serializers.RankTypeSerializer::class)
    val rank: RankType? = null,
) : Entity