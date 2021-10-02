package integration.db.repos

import db.repos.TeachersRepo
import kodein
import kotlinx.coroutines.runBlocking
import models.Teacher
import org.bson.types.ObjectId
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.kodein.di.instance
import org.litote.kmongo.id.toId
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TeachersRepoTest {

    private val repo: TeachersRepo by kodein.instance()
    private val teacher = Teacher(ObjectId("60a81be79827071039aac049").toId(), "NAME", "first", "place")

    @Order(1)
    @Test
    fun add_teacher_succeeds() {
        runBlocking {
            val result = repo.addTeacher(teacher)
            assertTrue(result.wasAcknowledged())
        }
    }

    @Order(2)
    @Test
    fun update_teacher_succeeds() {
        runBlocking {
            val result = repo.updateTeacher(teacher)
            println(result.modifiedCount)
            assertTrue(result.matchedCount == 1L)
        }
    }

    @Order(3)
    @Test
    fun get_allTeachers_succeeds() {
        runBlocking {
            val teachersList = repo.getAllTeachers()
            assertTrue(teachersList.isNotEmpty())
            assertTrue(teachersList.contains(teacher))
        }
    }

    @Order(4)
    @Test
    fun search_existingTeachers1_succeeds() {
        runBlocking {
            val teachersList = repo.searchForTeachers(Teacher(name = "na"))
            assertTrue(teachersList.isNotEmpty())
            assertTrue(teachersList.contains(teacher))
        }
    }

    @Order(5)
    @Test
    fun search_existingTeachers2_succeeds() {
        runBlocking {
            val teachersList = repo.searchForTeachers(Teacher(name = "na", firstName = "st"))
            assertTrue(teachersList.isNotEmpty())
            assertTrue(teachersList.contains(teacher))
        }
    }

    @Order(6)
    @Test
    fun search_nonExisting_teachers_fails() {
        runBlocking {
            val teachersList = repo.searchForTeachers(Teacher(name = "go"))
            assertTrue(teachersList.isEmpty())
            assertFalse(teachersList.contains(teacher))
        }
    }

    @Order(7)
    @Test
    fun delete_teacher_succeeds() {
        runBlocking {
            val result = repo.deleteTeacher(teacher)
            assertTrue(result != null)
        }
    }


}