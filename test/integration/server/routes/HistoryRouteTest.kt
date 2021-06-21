package integration.server.routes

import db.repos.HistoryRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import models.History
import module
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.litote.kmongo.id.toId
import server.routes.RoutingConstants
import utils.Extensions.toJsonElement
import utils.sealed.EntityType
import utils.sealed.HistoryType
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class HistoryRouteTest {

    private val historyPath = RoutingConstants.ROOT_ROUTE + RoutingConstants.HISTORY_ROUTE
    private val historyId = ObjectId("60a81be79827071039aac044").toId<History>()
    private val history = History(historyId, HistoryType.Delete, EntityType.Payment, "text")

    @Order(1)
    @Test
    fun test_post_add_succeeds(): Unit = withTestApplication(Application::module) {
        val call = handleRequest(HttpMethod.Post, historyPath) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(history.toJsonElement().toString())
        }

        with(call) {
            val content = response.content
            assertNotNull(content)
            assertTrue(content.contains(historyId.toString()))
        }
    }

    @Order(2)
    @Test
    fun get_one_succeeds() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "$historyPath/$historyId")) {
            val content = response.content
            assertEquals(HttpStatusCode.OK, response.status())
            assertNotNull(content)
            assertTrue(content.contains("text"))
        }
    }

    @Order(3)
    @Test
    fun get_all_succeeds() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, historyPath)) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @AfterAll
    fun deleteAddedHistory() {
        runBlocking {
            HistoryRepo().deleteHistory(history)
        }
    }
}