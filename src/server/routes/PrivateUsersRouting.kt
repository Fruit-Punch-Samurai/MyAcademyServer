package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.PrivateUser
import org.kodein.di.generic.instance
import server.routes.RoutingConstants.ID_PARAM_NAME
import server.routes.RoutingConstants.ID_PARAM_ROUTE

object PrivateUsersRouting {

    //TODO: Admin auth to add and delete and see all
    //TODO: Add in 2 places

    private val repo: MainRepo by kodein.instance()

    fun Route.setupPrivateUsersRoute() {
        route(RoutingConstants.PRIVATE_USERS_ROUTE) {
            setupGetRequests()
            setupPostRequests()
            setupDeleteRequests()
        }
    }

    private fun Route.setupGetRequests() {
        get {
            call.respond(repo.getAllPrivateUsers())
        }

        get(ID_PARAM_ROUTE) {
            val privateUser = repo.getPrivateUser(call.parameters[ID_PARAM_NAME]!!)

            if (privateUser != null) call.respond(privateUser)
            else call.response.status(HttpStatusCode.NotFound)

        }
    }


    private fun Route.setupPostRequests() {
        post {
            val privateUser = call.receive<PrivateUser>()
            call.respond(repo.addPrivateUser(privateUser))
            call.response.status(HttpStatusCode.OK)
        }
    }

    private fun Route.setupDeleteRequests() {
        delete(ID_PARAM_ROUTE) {
            val id = call.parameters[ID_PARAM_NAME] ?: return@delete
            call.defaultTextContentType(ContentType.Text.Plain)
            call.respond(repo.deletePrivateUser(id))
            call.response.status(HttpStatusCode.OK)
        }

    }

}