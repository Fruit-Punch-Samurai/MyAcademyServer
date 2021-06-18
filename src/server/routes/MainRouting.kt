package server.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import server.routes.HistoryRouting.setupHistoryRoute
import server.routes.LoginRouting.setupLoginsRoute
import server.routes.StudentsRouting.setupStudentsRoute
import server.routes.TeachersRouting.setupTeachersRoute
import server.routes.UsersRouting.setupUsersRoute
import server.security.SecurityConstants

object MainRouting {

    fun Application.setupRoutes() {
        routing {
            authenticate(SecurityConstants.REGULAR_USER_AUTH) {
                route(RoutingConstants.ROOT_ROUTE) {
                    setupUsersRoute()
                    setupStudentsRoute()
                    setupTeachersRoute()
                    setupHistoryRoute()
                }
            }
            setupLoginsRoute()

        }
    }

}