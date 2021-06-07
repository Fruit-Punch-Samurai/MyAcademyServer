package integration.db.repos

import db.repos.PrivateUsersRepo
import kodein
import kotlinx.coroutines.runBlocking
import models.PrivateUser
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.kodein.di.generic.instance
import org.litote.kmongo.id.toId
import utils.sealed.RankType
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PrivateUsersRepoTest {

    private val repo: PrivateUsersRepo by kodein.instance()
    private val privateUser = PrivateUser(ObjectId("60a81be79827071039aac049").toId(), "NAME", "first", RankType.Normal)

    @Order(1)
    @Test
    fun test_add_privateUser() {
        runBlocking {
            val result = repo.addPrivateUser(privateUser)
            assertTrue(result.wasAcknowledged())
        }
    }

    @Order(2)
    @Test
    fun test_update_privateUser() {
        runBlocking {
            val result = repo.updatePrivateUser(privateUser)
            println(result.modifiedCount)
            assertTrue(result.matchedCount == 1L)
        }
    }

    @Order(3)
    @Test
    fun test_get_all_privateUsers() {
        runBlocking {
            val privateUsersList = repo.getAllPrivateUsers()
            assertTrue(privateUsersList.isNotEmpty())
            assertTrue(privateUsersList.contains(privateUser))
        }
    }

    @Order(7)
    @Test
    fun test_delete_privateUser() {
        runBlocking {
            val result = repo.deletePrivateUser(privateUser)
            assertTrue(result != null)
        }
    }


}