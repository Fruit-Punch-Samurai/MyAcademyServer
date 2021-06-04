package integration.server.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import models.User
import module
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.litote.kmongo.id.toId
import server.routes.RoutingConstants
import utils.Extensions.toJsonElement
import utils.sealed.RankType
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UsersRouteTest {

    private val usersPath = RoutingConstants.ROOT_ROUTE + RoutingConstants.USERS_ROUTE
    private val userId = ObjectId("60a81be79827071039aac044").toId<User>()

    @Order(1)
    @Test
    fun test_post_request(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, usersPath) {
            addHeader(HttpHeaders.ContentType, "application/json")
            addHeader(HttpHeaders.Accept, "*/*")
            setBody(User(userId, "nomnom", RankType.Admin, true).toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.Accepted, response.status())
        }
    }

    @Order(2)
    @Test
    fun test_get_one_request() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "$usersPath/$userId")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(3)
    @Test
    fun test_get_all_request() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, usersPath)) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(4)
    @Test
    fun test_deleteOne_request() = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Delete, "$usersPath/$userId")

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }


}