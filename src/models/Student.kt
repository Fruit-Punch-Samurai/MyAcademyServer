package models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class Student(
    val _id: @Contextual Id<Student>? = null,
    val name: String? = null,
    val firstName: String? = null,
    val birthPlace: String? = null,
    val birthDate: LocalDateTime? = null,
    val gender: String? = null,
    val address: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val notes: String? = null,
    var date: LocalDateTime? = null,
) : Entity
