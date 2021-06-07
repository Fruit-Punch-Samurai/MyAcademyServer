package utils

import io.ktor.application.*
import io.ktor.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import models.Entity
import models.PrivateUser
import models.User
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

object Extensions {

    fun String?.emptyIfNull() = this ?: ""

    val json = Json { serializersModule = IdKotlinXSerializationModule }

    inline fun <reified T : Entity> T.toJsonElement(): JsonElement {
        return json.encodeToJsonElement(this)
    }

    inline fun <reified T : Entity> List<T>.toJsonElement(): JsonElement {
        return json.encodeToJsonElement(this)
    }

    inline fun <reified T : Entity> JsonElement.toEntity(): T {
        return json.decodeFromJsonElement(this) as T
    }

    suspend inline fun <reified T : Entity> ApplicationCall.jsonTo(): T {
        val jsonElement = receive<JsonElement>()
        return jsonElement.toEntity() as T
    }

    fun PrivateUser.toUser(): User {
        return User(this._id.cast(), this.name, this.rank)
    }
}