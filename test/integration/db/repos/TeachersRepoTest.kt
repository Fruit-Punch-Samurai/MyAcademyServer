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
import org.kodein.di.generic.instance
import org.litote.kmongo.id.toId
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TeachersRepoTest {

    private val repo: TeachersRepo by kodein.instance()
    private val teacher = Teacher(ObjectId("60a81be79827071039aac049").toId(), "NAME", "first", "place")

    @Order(1)
    @Test
    fun test_add_teacher() {
        runBlocking {
            val result = repo.addTeacher(teacher)
            assertTrue(result.wasAcknowledged())
        }
    }

    @Order(2)
    @Test
    fun test_update_teacher() {
        runBlocking {
            val result = repo.updateTeacher(teacher)
            println(result.modifiedCount)
            assertTrue(result.matchedCount == 1L)
        }
    }

    @Order(3)
    @Test
    fun test_get_all_teachers() {
        runBlocking {
            val teachersList = repo.getAllTeachers()
            assertTrue(teachersList.isNotEmpty())
            assertTrue(teachersList.contains(teacher))
        }
    }

    @Order(4)
    @Test
    fun test_search_for_existing_teachers_1() {
        runBlocking {
            val teachersList = repo.searchForTeachers(Teacher(name = "na"))
            assertTrue(teachersList.isNotEmpty())
            assertTrue(teachersList.contains(teacher))
        }
    }

    @Order(5)
    @Test
    fun test_search_for_existing_teachers_2() {
        runBlocking {
            val teachersList = repo.searchForTeachers(Teacher(name = "na", firstName = "st"))
            assertTrue(teachersList.isNotEmpty())
            assertTrue(teachersList.contains(teacher))
        }
    }

    @Order(6)
    @Test
    fun test_search_for_non_existing_teachers() {
        runBlocking {
            val teachersList = repo.searchForTeachers(teacher.copy(name = "go"))
            assertTrue(teachersList.isEmpty())
            assertFalse(teachersList.contains(teacher))
        }
    }

    @Order(7)
    @Test
    fun test_delete_teacher() {
        runBlocking {
            val result = repo.deleteTeacher(teacher)
            assertTrue(result != null)
        }
    }


}