package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.Credentials
import org.kodein.di.generic.instance
import utils.sealed.MyResult

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

            val result = repo.getPrivateUser(credentials)

            if (result is MyResult.Success) call.respond(result.value._id.toString())
            else call.respond(false)
        }
    }

}