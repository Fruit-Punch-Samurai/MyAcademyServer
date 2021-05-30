package models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class Teacher(
    override val _id: @Contextual Id<Customer>? = null,
    override val name: String? = null,
    override var firstName: String? = null,
    override var birthPlace: String? = null,
    override val birthDate: LocalDateTime? = null,
    override var gender: String? = null,
    override var address: String? = null,
    override var email: String? = null,
    override var phone: String? = null,
    override var notes: String? = null,
    override val date: LocalDateTime? = null
) : Customer()
