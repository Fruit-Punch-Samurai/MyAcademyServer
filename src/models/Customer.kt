package models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
abstract class Customer : Entity {
    @Suppress("PropertyName")
    abstract val _id: @Contextual Id<Customer>?
    abstract val name: String?
    abstract var firstName: String?
    abstract var birthPlace: String?
    abstract val birthDate: LocalDateTime?
    abstract var gender: String?
    abstract var address: String?
    abstract var email: String?
    abstract var phone: String?
    abstract var notes: String?
    abstract val date:  LocalDateTime?
}
