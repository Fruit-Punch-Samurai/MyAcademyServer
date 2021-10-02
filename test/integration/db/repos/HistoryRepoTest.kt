package integration.db.repos

import db.repos.HistoryRepo
import kodein
import kotlinx.coroutines.runBlocking
import models.History
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.kodein.di.instance
import org.litote.kmongo.id.toId
import utils.sealed.EntityType
import utils.sealed.HistoryType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class HistoryRepoTest {

    private val repo: HistoryRepo by kodein.instance()
    private val history =
        History(ObjectId("60a81be79827071039aac049").toId(), HistoryType.Add, EntityType.Student, "place")

    @Order(1)
    @Test
    fun add_history_succeeds() {
        runBlocking {
            val result = repo.addHistory(history)
            assertTrue(result.insertedId?.asObjectId()?.value.toString() == "60a81be79827071039aac049")
        }
    }

    @Order(2)
    @Test
    fun get_allHistories_succeeds() {
        runBlocking {
            val historiesList = repo.getAllHistories()
            assertTrue(historiesList.isNotEmpty())
            assertTrue(historiesList.contains(history))
        }
    }

    @Order(3)
    @Test
    fun delete_history_succeeds() {
        runBlocking {
            val deletedHistory = repo.deleteHistory(history)
            assertEquals(deletedHistory, history)
        }
    }

}