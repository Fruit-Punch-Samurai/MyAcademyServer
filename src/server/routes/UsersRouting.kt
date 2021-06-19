package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.User
import org.kodein.di.generic.instance
import server.routes.RoutingConstants.ID_PARAM_NAME
import server.routes.RoutingConstants.ID_PARAM_ROUTE
import utils.sealed.MyResult

object UsersRouting {

    //TODO: Add in 2 places
    private val repo: MainRepo by kodein.instance()

    fun Route.setupUsersRoute() {
        route(RoutingConstants.USERS_ROUTE) {
            setupGetRequests()
            setupPostRequests()
            setupDeleteRequests()
        }
    }

    private fun Route.setupGetRequests() {
        get {
            val result = repo.getAllUsers()
            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }

        get(ID_PARAM_ROUTE) {
            val result = repo.getUser(call.parameters[ID_PARAM_NAME]!!)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }
    }


    private fun Route.setupPostRequests() {
        post {
            val user = call.receive<User>()
            val result = repo.addUser(user)

            if (result is MyResult.Success) {
                call.response.status(HttpStatusCode.Accepted)
            } else call.response.status(HttpStatusCode.NotAcceptable)
        }
    }

    private fun Route.setupDeleteRequests() {
        delete(ID_PARAM_ROUTE) {
            val id = call.parameters[ID_PARAM_NAME] ?: return@delete
            val result = repo.deleteUser(id)
            if (result is MyResult.Success) call.response.status(HttpStatusCode.OK)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

    }

}