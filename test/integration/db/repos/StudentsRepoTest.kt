package integration.db.repos

import db.repos.StudentsRepo
import io.ktor.application.*
import io.ktor.server.testing.*
import kodein
import kotlinx.coroutines.runBlocking
import models.Student
import module
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.kodein.di.generic.instance
import org.litote.kmongo.id.toId
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StudentsRepoTest {

    private val repo: StudentsRepo by kodein.instance()
    private val student = Student(ObjectId("60a81be79827071039aac049").toId(), "name", "first", "place")

    @Order(1)
    @Test
    fun test_add_student() {
        runBlocking {
            val result = repo.addStudent(student)
            assertTrue(result.wasAcknowledged())
        }
    }

    @Order(2)
    @Test
    fun test_get_all_students() = withTestApplication(Application::module) {
        runBlocking {
            val studentsList = repo.getAllStudents()
            assertTrue(studentsList.isNotEmpty())
            assertTrue(studentsList.contains(student))
        }
    }

    @Order(3)
    @Test
    fun test_delete_student() {
        runBlocking {
            val result = repo.deleteStudent(student)
            assertTrue(result.wasAcknowledged())
        }
    }


}