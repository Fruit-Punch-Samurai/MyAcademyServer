package models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class Teacher(
    val _id: @Contextual Id<Teacher>? = null,
    val name: String? = null,
    var firstName: String? = null,
    var birthPlace: String? = null,
    val birthDate: LocalDateTime? = null,
    var gender: String? = null,
    var address: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var notes: String? = null,
    val date: LocalDateTime? = null
) : Entity
