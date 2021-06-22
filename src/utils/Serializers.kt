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

    object HistoryTypeSerializer : KSerializer<HistoryType> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("historyType", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: HistoryType) {
            when (value) {
                is HistoryType.Add -> encoder.encodeString(value.value)
                is HistoryType.Delete -> encoder.encodeString(value.value)
                is HistoryType.Modify -> encoder.encodeString(value.value)
                is HistoryType.Other -> encoder.encodeString(value.value)
            }
        }

        override fun deserialize(decoder: Decoder): HistoryType {
            return when (decoder.decodeString()) {
                HistoryType.Add.value -> HistoryType.Add
                HistoryType.Delete.value -> HistoryType.Delete
                HistoryType.Modify.value -> HistoryType.Modify
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
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("rankType", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: RankType) {
            when (value) {
                is RankType.Admin -> encoder.encodeString(value.value)
                is RankType.Normal -> encoder.encodeString(value.value)
                is RankType.Guest -> encoder.encodeString(value.value)
            }
        }

        override fun deserialize(decoder: Decoder): RankType {
            return when (decoder.decodeString()) {
                RankType.Admin.value -> RankType.Admin
                RankType.Normal.value -> RankType.Normal
                RankType.Guest.value -> RankType.Guest
                else -> RankType.Guest
            }
        }
    }

}