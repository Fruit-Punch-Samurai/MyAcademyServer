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
import utils.sealed.MyResult

object PrivateUsersRouting {

    //TODO: Admin auth to add and delete and see all, or just make another app.
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
        //TODO: remove get all request
        get {
            call.respond((repo.getAllPrivateUsers() as MyResult.Success).value)
        }
        get(ID_PARAM_ROUTE) {
            val privateUser = call.parameters[ID_PARAM_NAME]?.let { id -> repo.getPrivateUser(id) }
            if (privateUser is MyResult.Success) call.response.status(HttpStatusCode.OK)
            else call.response.status(HttpStatusCode.Unauthorized)
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
            val result = repo.deletePrivateUser(id)

            if (result is MyResult.Success) call.response.status(HttpStatusCode.OK)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

    }

}