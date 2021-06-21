package utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import utils.sealed.EntityType
import utils.sealed.HistoryType
import utils.sealed.PaymentType
import utils.sealed.RankType

object Serializers {

    //TODO: Use constant values in sealed classes for serializing

    object HistoryTypeSerializer : KSerializer<HistoryType> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("type", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: HistoryType) {
            when (value) {
                HistoryType.Add -> encoder.encodeString("add")
                HistoryType.Delete -> encoder.encodeString("delete")
                HistoryType.Modify -> encoder.encodeString("modify")
                else -> encoder.encodeString("other")
            }
        }

        override fun deserialize(decoder: Decoder): HistoryType {
            return when (decoder.decodeString()) {
                "add" -> HistoryType.Add
                "delete" -> HistoryType.Delete
                "modify" -> HistoryType.Modify
                else -> HistoryType.Other
            }
        }
    }

    object EntityTypeSerializer : KSerializer<EntityType> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("entityType", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: EntityType) {
            when (value) {
                is EntityType.Student -> encoder.encodeString(value.value)
                is EntityType.Teacher -> encoder.encodeString(value.value)
                is EntityType.Payment -> encoder.encodeString(value.value)
                is EntityType.Other -> encoder.encodeString(value.value)
            }
        }

        override fun deserialize(decoder: Decoder): EntityType {
            return when (decoder.decodeString()) {
                EntityType.Student.value -> EntityType.Student
                EntityType.Teacher.value -> EntityType.Teacher
                EntityType.Payment.value -> EntityType.Payment
                else -> EntityType.Other
            }
        }
    }

    object PaymentTypeSerializer : KSerializer<PaymentType> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("paymentType", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: PaymentType) {
            when (value) {
                is PaymentType.Incoming -> encoder.encodeString(value.value)
                is PaymentType.Outgoing -> encoder.encodeString(value.value)
                is PaymentType.Other -> encoder.encodeString(value.value)
            }
        }

        override fun deserialize(decoder: Decoder): PaymentType {
            return when (decoder.decodeString()) {
                PaymentType.Incoming.value -> PaymentType.Incoming
                PaymentType.Outgoing.value -> PaymentType.Outgoing
                else -> PaymentType.Other
            }
        }
    }

    object RankTypeSerializer : KSerializer<RankType> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("rank", PrimitiveKind.STRING)

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