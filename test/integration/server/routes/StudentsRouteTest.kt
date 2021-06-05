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

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StudentsRouteTest {

    private val studentsPath = RoutingConstants.ROOT_ROUTE + RoutingConstants.STUDENTS_ROUTE
    private val studentId = ObjectId("60a81be79827071039aac044").toId<Student>()

    @Order(1)
    @Test
    fun test_post_request(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, studentsPath) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Student(studentId, "nomnom", "first", "place").toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.Accepted, response.status())
        }
    }

    @Order(2)
    @Test
    fun test_get_one_request() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "$studentsPath/$studentId")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(3)
    @Test
    fun test_get_all_request() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, studentsPath)) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(4)
    @Test
    fun test_deleteOne_request() = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Delete, "$studentsPath/$studentId")

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }


}