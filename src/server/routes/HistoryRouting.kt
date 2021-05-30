package server.routes

import db.repos.MainRepo
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kodein
import models.History
import org.kodein.di.generic.instance
import org.litote.kmongo.json
import utils.DateManager
import utils.HistoryType

object HistoryRouting {

    private val repo: MainRepo by kodein.instance()


    fun Route.setupHistoryRoute() {
        route(RoutingConstants.HISTORY_ROUTE) {
            get {
                call.respond(repo.getAllHistories().json)
            }
            post {
                repo.addHistory(
                    History(
                        null,
                        HistoryType.Delete,
                        "history goooooo1!!",
                        "uid 5236",
                        date = DateManager.getCurrentLocalDate()
                    )
                )
            }
            delete {

            }
        }

    }

}