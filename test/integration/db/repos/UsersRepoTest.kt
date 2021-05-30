package integration.db.repos

import db.repos.UsersRepo
import io.ktor.application.*
import io.ktor.server.testing.*
import kodein
import kotlinx.coroutines.runBlocking
import models.User
import module
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.kodein.di.generic.instance
import org.litote.kmongo.id.toId
import utils.RankType
import kotlin.test.Test
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UsersRepoTest {

    private val repo: UsersRepo by kodein.instance()
    private val user = User(ObjectId("60a81be79827071039aac049").toId(), "name", RankType.Normal, true)

    @Order(1)
    @Test
    fun test_add_user() {
        runBlocking {
            val result = repo.addUser(user)
            assertTrue(result.wasAcknowledged())
        }
    }

    @Order(2)
    @Test
    fun test_get_all_users() = withTestApplication(Application::module) {
        runBlocking {
            val usersList = repo.getAllUsers()
            assertTrue(usersList.isNotEmpty())
            assertTrue(usersList.contains(user))
        }
    }

    @Order(3)
    @Test
    fun test_delete_user() {
        runBlocking {
            val result = repo.deleteUser(user)
            assertTrue(result.wasAcknowledged())
        }
    }


}