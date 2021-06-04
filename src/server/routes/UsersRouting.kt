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

    //TODO: Admin auth to add and delete
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
            call.respond(repo.getAllUsers())
        }

        get(ID_PARAM_ROUTE) {
            val user = repo.getUser(call.parameters[ID_PARAM_NAME]!!)

            if (user != null) call.respond(user)
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
            call.defaultTextContentType(ContentType.Text.Plain)
            call.respond(repo.deleteUser(id))
            call.response.status(HttpStatusCode.OK)
        }

    }

}