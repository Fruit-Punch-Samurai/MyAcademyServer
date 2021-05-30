package models

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val name: String? = null,
    val password: String? = null
) : Entity
