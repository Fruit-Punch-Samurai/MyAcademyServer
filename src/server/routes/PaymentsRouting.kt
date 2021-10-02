package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.Payment
import org.kodein.di.instance
import utils.sealed.MyResult

object PaymentsRouting {

    private val repo: MainRepo by kodein.instance()

    fun Route.setupPaymentsRoute() {
        route(RoutingConstants.PAYMENTS_ROUTE) {
            setupGetRequests()
            setupPostRequests()
            setupDeleteRequests()
        }
    }

    private fun Route.setupGetRequests() {
        get {
            val result = repo.getLastPayments()
            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }

        get(RoutingConstants.ID_PARAM_ROUTE) {
            val result = repo.getPayment(call.parameters[RoutingConstants.ID_PARAM_NAME]!!)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }
    }


    private fun Route.setupPostRequests() {
        post {
            val payment = call.receive<Payment>()
            val result = repo.addPayment(payment)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

        post(RoutingConstants.ID_PARAM_ROUTE) {
            val payment = call.receive<Payment>()
            val result = repo.updatePayment(payment)

            if (result is MyResult.Success) call.response.status(HttpStatusCode.Accepted)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

        post(RoutingConstants.SEARCH_ROUTE) {
            val payment = call.receive<Payment>()
            val result = repo.searchPayments(payment)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }

    }

    private fun Route.setupDeleteRequests() {
        delete(RoutingConstants.ID_PARAM_ROUTE) {
            val id = call.parameters[RoutingConstants.ID_PARAM_NAME] ?: return@delete
            val result = repo.deletePayment(id)
            if (result is MyResult.Success) call.response.status(HttpStatusCode.OK)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

    }

}