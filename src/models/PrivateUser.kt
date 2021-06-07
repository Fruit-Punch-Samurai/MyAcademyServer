package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.id.toId
import utils.Serializers
import utils.sealed.RankType

@Serializable
data class PrivateUser(
    val _id: @Contextual Id<PrivateUser> = ObjectId.get().toId(),
    val name: String,
    val password: String,
    @Serializable(with = Serializers.RankTypeSerializer::class)
    val rank: RankType = RankType.Guest,
) : Entity