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
            call.respond(repo.getAllTeachers())
        }

        get(RoutingConstants.ID_PARAM_ROUTE) {
            val teacher = repo.getTeacher(call.parameters[RoutingConstants.ID_PARAM_NAME]!!)

            if (teacher != null) call.respond(teacher)
            else call.response.status(HttpStatusCode.NotFound)

        }
    }


    private fun Route.setupPostRequests() {
        post {
            val teacher = call.receive<Teacher>()
            call.respond(repo.addTeacher(teacher))
            call.response.status(HttpStatusCode.OK)
        }
    }

    private fun Route.setupDeleteRequests() {
        delete(RoutingConstants.ID_PARAM_ROUTE) {
            val id = call.parameters[RoutingConstants.ID_PARAM_NAME] ?: return@delete
            call.defaultTextContentType(ContentType.Text.Plain)
            call.respond(repo.deleteTeacher(id))
            call.response.status(HttpStatusCode.OK)
        }

    }

}