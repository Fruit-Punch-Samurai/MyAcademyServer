package integration.server.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import models.Payment
import module
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.litote.kmongo.id.toId
import server.routes.RoutingConstants
import utils.Extensions.toJsonElement
import utils.sealed.EntityType
import utils.sealed.PaymentType
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PaymentsRouteTest {

    private val paymentsPath = RoutingConstants.ROOT_ROUTE + RoutingConstants.PAYMENTS_ROUTE
    private val paymentId = ObjectId("60a81be79827071039aac044").toId<Payment>()
    private val payment = Payment(paymentId, EntityType.Teacher, PaymentType.Outgoing, 2500F)

    @Order(1)
    @Test
    fun test_post_add_request(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, paymentsPath) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(payment.toJsonElement().toString())
        }

        with(call) {
            assertEquals(response.content, paymentId.toString())
        }
    }

    @Order(2)
    @Test
    fun test_post_update_request(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$paymentsPath/$paymentId") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(payment.copy(amount = 7500F).toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.Accepted, response.status())
        }
    }

    @Order(3)
    @Test
    fun test_post_search_request_1(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$paymentsPath${RoutingConstants.SEARCH_ROUTE}") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(
                Payment(entityType = EntityType.Teacher, paymentType = PaymentType.Outgoing).toJsonElement().toString()
            )
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val content = response.content
            assertNotNull(content)
            println(content)
            assertTrue { content.contains("teacher") }
        }
    }

    @Order(4)
    @Test
    fun test_post_search_request_2(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, "$paymentsPath${RoutingConstants.SEARCH_ROUTE}") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Payment(amount = 7500F).toJsonElement().toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val content = response.content
            assertNotNull(content)
            println(content)
            assertTrue { content.contains("7500") }
        }
    }


    @Order(5)
    @Test
    fun test_get_one_request() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "$paymentsPath/$paymentId")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(6)
    @Test
    fun test_get_all_request() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, paymentsPath)) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Order(7)
    @Test
    fun test_deleteOne_request() = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Delete, "$paymentsPath/$paymentId")

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }


}