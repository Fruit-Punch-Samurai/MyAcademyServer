package integration.server.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import models.Teacher
import module
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.litote.kmongo.id.toId
import server.routes.RoutingConstants
import utils.Extensions.toJsonElement
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TeachersRouteTest {

    private val teachersPath = RoutingConstants.ROOT_ROUTE + RoutingConstants.TEACHERS_ROUTE
    private val teacherId = ObjectId("60a81be79827071039aac044").toId<Teacher>()
    private val teacher = Teacher(teacherId, "namr", "first", "place")

    @Order(1)
    @Test
    fun post_add_succeeds(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, teachersPath) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(teacher.toJsonElement().toString())
        }

        with(call) {
            assertEquals(response.content, teacherId.toString())
        }
    }

    @Order(2)
    @Test
    fun post_update_succeeds(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$teachersPath/$teacherId") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(teacher.copy(name = "nono").toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.Accepted, response.status())
        }
    }

    @Order(3)
    @Test
    fun post_search1_succeeds(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$teachersPath${RoutingConstants.SEARCH_ROUTE}") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Teacher(name = "no", firstName = "st").toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val content = response.content
            assertNotNull(content)
            println(content)
            assertTrue { content.contains("nono") }
        }
    }

    @Order(4)
    @Test
    fun post_search2_succeeds(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$teachersPath${RoutingConstants.SEARCH_ROUTE}") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Teacher(name = "no").toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val content = response.content
            assertNotNull(content)
            println(content)
            assertTrue { content.contains("nono") }
        }
    }


    @Order(5)
    @Test
    fun get_one_succeeds() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "$teachersPath/$teacherId")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(6)
    @Test
    fun get_all_succeeds() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, teachersPath)) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(7)
    @Test
    fun delete_one_succeeds() = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Delete, "$teachersPath/$teacherId")

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }


}