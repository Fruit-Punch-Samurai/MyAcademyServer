package server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule
import server.security.Auth.setupAdminUserAuth
import server.security.Auth.setupRegularUserAuth

object Plugins {

    fun Application.setupFeatures() {
        install(CallLogging)

        install(ContentNegotiation) {
            json(Json {
                serializersModule = IdKotlinXSerializationModule
            })
        }

        install(Authentication) {
            runBlocking {
                setupRegularUserAuth()
                setupAdminUserAuth()
            }
        }
    }
}