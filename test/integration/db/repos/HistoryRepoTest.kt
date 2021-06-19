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
import org.kodein.di.generic.instance
import org.litote.kmongo.id.toId
import utils.sealed.EntityType
import utils.sealed.HistoryType
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class HistoryRepoTest {

    private val repo: HistoryRepo by kodein.instance()
    private val history =
        History(ObjectId("60a81be79827071039aac049").toId(), HistoryType.Add, EntityType.Student, "place")

    @Order(1)
    @Test
    fun test_add_history() {
        runBlocking {
            val result = repo.addHistory(history)
            assertTrue(result.insertedId?.asObjectId()?.value.toString() == "60a81be79827071039aac049")
        }
    }

    @Order(2)
    @Test
    fun test_get_all_histories() {
        runBlocking {
            val historiesList = repo.getAllHistories()
            assertTrue(historiesList.isNotEmpty())
            assertTrue(historiesList.contains(history))
        }
    }


}