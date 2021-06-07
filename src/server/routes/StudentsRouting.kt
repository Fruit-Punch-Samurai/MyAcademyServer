package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.Student
import org.kodein.di.generic.instance
import utils.sealed.MyResult

object StudentsRouting {

    private val repo: MainRepo by kodein.instance()

    fun Route.setupStudentsRoute() {
        route(RoutingConstants.STUDENTS_ROUTE) {
            setupGetRequests()
            setupPostRequests()
            setupDeleteRequests()
        }
    }

    private fun Route.setupGetRequests() {
        get {
            val result = repo.getAllStudents()
            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }

        get(RoutingConstants.ID_PARAM_ROUTE) {
            val result = repo.getStudent(call.parameters[RoutingConstants.ID_PARAM_NAME]!!)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }
    }

    private fun Route.setupPostRequests() {
        post {
            val student = call.receive<Student>()
            val result = repo.addStudent(student)

            if (result is MyResult.Success) call.response.status(HttpStatusCode.Accepted)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

        post(RoutingConstants.ID_PARAM_ROUTE) {
            val student = call.receive<Student>()
            val result = repo.updateStudent(student)

            if (result is MyResult.Success) call.response.status(HttpStatusCode.Accepted)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

        post(RoutingConstants.SEARCH_ROUTE) {
            val student = call.receive<Student>()
            val result = repo.searchStudents(student)

            if (result is MyResult.Success) call.respond(result.value)
            else call.response.status(HttpStatusCode.NotFound)
        }

    }

    private fun Route.setupDeleteRequests() {
        delete(RoutingConstants.ID_PARAM_ROUTE) {
            val id = call.parameters[RoutingConstants.ID_PARAM_NAME] ?: return@delete
            val result = repo.deleteStudent(id)
            if (result is MyResult.Success) call.response.status(HttpStatusCode.OK)
            else call.response.status(HttpStatusCode.NotAcceptable)
        }

    }

}