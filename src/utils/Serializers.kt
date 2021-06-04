package utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import utils.sealed.HistoryType
import utils.sealed.RankType

object Serializers {

    object HistoryTypeSerializer : KSerializer<HistoryType> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("type", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: HistoryType) {
            when (value) {
                HistoryType.Add -> encoder.encodeString("add")
                HistoryType.Delete -> encoder.encodeString("delete")
                HistoryType.Update -> encoder.encodeString("update")
                else -> encoder.encodeString("other")
            }
        }

        override fun deserialize(decoder: Decoder): HistoryType {
            return when (decoder.decodeString()) {
                "add" -> HistoryType.Add
                "delete" -> HistoryType.Delete
                "update" -> HistoryType.Update
                else -> HistoryType.Other
            }
        }
    }

    object RankTypeSerializer : KSerializer<RankType> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("type", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: RankType) {
            when (value) {
                RankType.Admin -> encoder.encodeString("admin")
                RankType.Normal -> encoder.encodeString("normal")
                RankType.Guest -> encoder.encodeString("guest")
            }
        }

        override fun deserialize(decoder: Decoder): RankType {
            return when (decoder.decodeString()) {
                "admin" -> RankType.Admin
                "normal" -> RankType.Normal
                "guest" -> RankType.Guest
                else -> RankType.Guest
            }
        }
    }

}