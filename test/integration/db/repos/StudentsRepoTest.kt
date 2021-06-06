package integration.db.repos

import db.repos.StudentsRepo
import kodein
import kotlinx.coroutines.runBlocking
import models.Student
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.kodein.di.generic.instance
import org.litote.kmongo.id.toId
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StudentsRepoTest {

    private val repo: StudentsRepo by kodein.instance()
    private val student = Student(ObjectId("60a81be79827071039aac049").toId(), "NAME", "first", "place")

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
    fun test_update_student() {
        runBlocking {
            val result = repo.updateStudent(student)
            println(result.modifiedCount)
            assertTrue(result.matchedCount == 1L)
        }
    }

    @Order(3)
    @Test
    fun test_get_all_students() {
        runBlocking {
            val studentsList = repo.getAllStudents()
            assertTrue(studentsList.isNotEmpty())
            assertTrue(studentsList.contains(student))
        }
    }

    @Order(4)
    @Test
    fun test_search_for_existing_students_1() {
        runBlocking {
            val studentsList = repo.searchForStudents(Student(name = "na"))
            assertTrue(studentsList.isNotEmpty())
            assertTrue(studentsList.contains(student))
        }
    }

    @Order(5)
    @Test
    fun test_search_for_existing_students_2() {
        runBlocking {
            val studentsList = repo.searchForStudents(Student(name = "na", firstName = "st"))
            assertTrue(studentsList.isNotEmpty())
            assertTrue(studentsList.contains(student))
        }
    }

    @Order(6)
    @Test
    fun test_search_for_non_existing_students() {
        runBlocking {
            val studentsList = repo.searchForStudents(student.copy(name = "go"))
            assertTrue(studentsList.isEmpty())
            assertFalse(studentsList.contains(student))
        }
    }

    @Order(7)
    @Test
    fun test_delete_student() {
        runBlocking {
            val result = repo.deleteStudent(student)
            assertTrue(result!=null)
        }
    }


}