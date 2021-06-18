package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.History
import org.kodein.di.generic.instance

object HistoryRouting {

    private val repo: MainRepo by kodein.instance()

    fun Route.setupHistoryRoute() {
        route(RoutingConstants.HISTORY_ROUTE) {
            setupGetRequests()
            setupPostRequests()
        }
    }

    private fun Route.setupGetRequests() {
        get {
            call.respond(repo.getAllHistories())
        }

        get(RoutingConstants.ID_PARAM_ROUTE) {
            val history = repo.getHistory(call.parameters[RoutingConstants.ID_PARAM_NAME]!!)

            if (history != null) call.respond(history)
            else call.response.status(HttpStatusCode.NotFound)

        }
    }


    private fun Route.setupPostRequests() {
        post {
            val history = call.receive<History>()
            call.respond(repo.addHistory(history))
            call.response.status(HttpStatusCode.OK)
        }
    }

}