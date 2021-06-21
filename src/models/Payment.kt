package models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import utils.Serializers
import utils.sealed.EntityType
import utils.sealed.PaymentType

//TODO: Add imprDate and amount not null

@Serializable
data class Payment(
    val _id: @Contextual Id<Payment>? = null,
    @Serializable(with = Serializers.EntityTypeSerializer::class)
    val entityType: EntityType? = null,
    @Serializable(with = Serializers.PaymentTypeSerializer::class)
    val paymentType: PaymentType? = null,
    val amount: Float? = null,
    val entityID: String? = null,
    val notesToPrint: String? = null,
    val notes: String? = null,
    var date: LocalDateTime? = null,
) : Entity