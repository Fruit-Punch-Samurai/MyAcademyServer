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
    fun add_student_succeeds() {
        runBlocking {
            val result = repo.addStudent(student)
            assertTrue(result.wasAcknowledged())
        }
    }

    @Order(2)
    @Test
    fun update_existingStudent_succeeds() {
        runBlocking {
            val result = repo.updateStudent(student)
            println(result.modifiedCount)
            assertTrue(result.matchedCount == 1L)
        }
    }

    @Order(3)
    @Test
    fun update_nonExistingStudent_fails() {
        runBlocking {
            val result = repo.updateStudent(student.copy(ObjectId("60a81be79827071039aac048").toId()))
            println(result.modifiedCount)
            assertTrue(result.matchedCount == 0L)
        }
    }

    @Order(4)
    @Test
    fun get_allStudents_succeeds() {
        runBlocking {
            val studentsList = repo.getAllStudents()
            assertTrue(studentsList.isNotEmpty())
            assertTrue(studentsList.contains(student))
        }
    }

    @Order(5)
    @Test
    fun search_existingStudents1_succeeds() {
        runBlocking {
            val studentsList = repo.searchForStudents(Student(name = "na"))
            assertTrue(studentsList.isNotEmpty())
            assertTrue(studentsList.contains(student))
        }
    }

    @Order(6)
    @Test
    fun search_existingStudents2_succeeds() {
        runBlocking {
            val studentsList = repo.searchForStudents(Student(name = "na", firstName = "st"))
            assertTrue(studentsList.isNotEmpty())
            assertTrue(studentsList.contains(student))
        }
    }

    @Order(7)
    @Test
    fun search_nonExistingStudents_fails() {
        runBlocking {
            val studentsList = repo.searchForStudents(Student(name = "go"))
            assertTrue(studentsList.isEmpty())
            assertFalse(studentsList.contains(student))
        }
    }

    @Order(8)
    @Test
    fun delete_student_succeeds() {
        runBlocking {
            val result = repo.deleteStudent(student)
            assertTrue(result != null)
        }
    }


}