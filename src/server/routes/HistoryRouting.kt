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
import utils.sealed.MyResult

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
            val result = repo.getAllHistories()
            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }

        get(RoutingConstants.ID_PARAM_ROUTE) {
            val result = repo.getHistory(call.parameters[RoutingConstants.ID_PARAM_NAME]!!)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }
    }


    private fun Route.setupPostRequests() {
        post {
            val history = call.receive<History>()
            val result = repo.addHistory(history)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

    }

}