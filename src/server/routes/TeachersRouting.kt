package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.Teacher
import org.kodein.di.generic.instance
import org.litote.kmongo.json
import utils.DateManager

object TeachersRouting {

    private val repo: MainRepo by kodein.instance()

     fun Route.setupTeachersRoute() {
        route(RoutingConstants.TEACHERS_ROUTE) {
            get {
                call.respond(repo.getAllTeachers().json)

            }
            post {
                repo.addTeacher(
                    Teacher(
                        null,
                        "Namae",
                        "firstnamaewa!!",
                        "BPLACE",
                        date = DateManager.getCurrentLocalDate()
                    )
                )
            }
            delete {

            }
        }

    }

}