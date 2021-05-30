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
            call.respond(repo.getAllStudents())
        }

        get(RoutingConstants.ID_PARAM_ROUTE) {
            val student = repo.getStudent(call.parameters[RoutingConstants.ID_PARAM_NAME]!!)

            if (student != null) call.respond(student)
            else call.response.status(HttpStatusCode.NotFound)

        }
    }


    private fun Route.setupPostRequests() {
        post {
            val student = call.receive<Student>()
            call.respond(repo.addStudent(student))
            call.response.status(HttpStatusCode.OK)
        }
    }

    private fun Route.setupDeleteRequests() {
        delete(RoutingConstants.ID_PARAM_ROUTE) {
            val id = call.parameters[RoutingConstants.ID_PARAM_NAME] ?: return@delete
            call.defaultTextContentType(ContentType.Text.Plain)
            call.respond(repo.deleteStudent(id))
            call.response.status(HttpStatusCode.OK)
        }

    }
}