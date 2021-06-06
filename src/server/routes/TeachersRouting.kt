package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.Teacher
import org.kodein.di.generic.instance
import utils.sealed.MyResult

object TeachersRouting {

    private val repo: MainRepo by kodein.instance()

    fun Route.setupTeachersRoute() {
        route(RoutingConstants.TEACHERS_ROUTE) {
            setupGetRequests()
            setupPostRequests()
            setupDeleteRequests()
        }
    }

    private fun Route.setupGetRequests() {
        get {
            val result = repo.getAllTeachers()
            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }

        get(RoutingConstants.ID_PARAM_ROUTE) {
            val result = repo.getTeacher(call.parameters[RoutingConstants.ID_PARAM_NAME]!!)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }
    }


    private fun Route.setupPostRequests() {
        post {
            val teacher = call.receive<Teacher>()
            val result = repo.addTeacher(teacher)

            if (result is MyResult.Success) call.response.status(HttpStatusCode.Accepted)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

        post(RoutingConstants.ID_PARAM_ROUTE) {
            val teacher = call.receive<Teacher>()
            val result = repo.updateTeacher(teacher)

            if (result is MyResult.Success) call.response.status(HttpStatusCode.Accepted)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

        post(RoutingConstants.SEARCH_ROUTE) {
            val teacher = call.receive<Teacher>()
            val result = repo.searchTeachers(teacher)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }

    }

    private fun Route.setupDeleteRequests() {
        delete(RoutingConstants.ID_PARAM_ROUTE) {
            val id = call.parameters[RoutingConstants.ID_PARAM_NAME] ?: return@delete
            val result = repo.deleteTeacher(id)
            if (result is MyResult.Success) call.response.status(HttpStatusCode.OK)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

    }

}