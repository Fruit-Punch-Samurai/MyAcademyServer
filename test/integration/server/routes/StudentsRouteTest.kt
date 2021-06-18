package integration.server.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import models.Student
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
class StudentsRouteTest {

    private val studentsPath = RoutingConstants.ROOT_ROUTE + RoutingConstants.STUDENTS_ROUTE
    private val studentId1 = ObjectId("60a81be79827071039aac044").toId<Student>()
    private val studentId2 = ObjectId("60a81be79827071039aac023").toId<Student>()
    private val student = Student(studentId1, "namr", "first", "place")

    @Order(1)
    @Test
    fun test_post_add_request(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, studentsPath) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(student.toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.Accepted, response.status())
        }
    }

    @Order(2)
    @Test
    fun test_post_update_request(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$studentsPath/$studentId1") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(student.copy(name = "nono").toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.Accepted, response.status())
        }
    }

    @Test
    fun test_post_update_non_existing_request(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$studentsPath/$studentId2") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(student.copy(name = "nono").toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.NotAcceptable, response.status())
        }
    }

    @Order(3)
    @Test
    fun test_post_search_request_1(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$studentsPath${RoutingConstants.SEARCH_ROUTE}") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Student(name = "no", firstName = "st").toJsonElement().toString())
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
    fun test_post_search_request_2(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$studentsPath${RoutingConstants.SEARCH_ROUTE}") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(student.copy(name = "no").toJsonElement().toString())
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
    fun test_get_one_request() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "$studentsPath/$studentId1")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(6)
    @Test
    fun test_get_all_request() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, studentsPath)) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(7)
    @Test
    fun test_deleteOne_request() = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Delete, "$studentsPath/$studentId1")

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }


}