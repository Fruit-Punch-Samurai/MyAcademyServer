package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.Credentials
import org.kodein.di.generic.instance

object LoginRouting {


    private val repo: MainRepo by kodein.instance()

    fun Route.setupLoginsRoute() {
        route(RoutingConstants.LOGIN_ROUTE) {
            setupPostRequests()
        }
    }

    //TODO: Validate inputs
    private fun Route.setupPostRequests() {
        post {
            val credentials = call.receive<Credentials>()
            if (credentials.name.isNullOrEmpty()) return@post
            if (credentials.password.isNullOrEmpty()) return@post

            val userExists = repo.getPrivateUser(credentials.name, credentials.password) != null

            call.respond(userExists)
            call.response.status(HttpStatusCode.OK)
        }
    }

}