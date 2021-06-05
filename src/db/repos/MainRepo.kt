package db.repos

import kodein
import models.*
import org.kodein.di.generic.instance
import utils.sealed.MyResult

class MainRepo {

    //TODO: Sealed class for Result, can be success or failure
    //TODO: Make little Repos private + DI
    //TODO: Delete requests return true even if Entity doesn't exist

    private val pUsersRepo: PrivateUsersRepo by kodein.instance()
    private val usersRepo: UsersRepo by kodein.instance()
    private val studentsRepo: StudentsRepo by kodein.instance()
    private val teachersRepo: TeachersRepo by kodein.instance()
    private val historyRepo: HistoryRepo by kodein.instance()

    suspend fun getAllUsers(): MyResult<List<User>> {
        return try {
            val list = usersRepo.getAllUsers()
            if (list.isEmpty()) MyResult.Failure(msg = "No users")
            else MyResult.Success(list)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun getUser(id: String): MyResult<User> {
        return try {
            val user = usersRepo.getUser(id)
            return if (user == null) MyResult.Failure(msg = "User not found")
            else MyResult.Success(user)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun addUser(user: User): MyResult<Unit> {
        return try {
            val acknowledged = usersRepo.addUser(user).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun deleteUser(id: String): MyResult<Unit> {
        return try {
            val result = usersRepo.deleteUser(id).wasAcknowledged()
            if (result) MyResult.Success(Unit) else MyResult.Failure(msg = "Delete failed")
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }


    suspend fun getAllPrivateUsers() = pUsersRepo.getAllPrivateUsers()
    suspend fun getPrivateUser(id: String) = pUsersRepo.getPrivateUser(id)
    suspend fun addPrivateUser(privateUser: PrivateUser) = pUsersRepo.addPrivateUser(privateUser).wasAcknowledged()
    suspend fun deletePrivateUser(id: String) = pUsersRepo.deletePrivateUser(id).wasAcknowledged()
    suspend fun getPrivateUser(name: String, pass: String) = pUsersRepo.getPrivateUser(name, pass)


    suspend fun getAllStudents(): MyResult<List<Student>> {
        return try {
            val list = studentsRepo.getAllStudents()
            if (list.isEmpty()) MyResult.Failure(msg = "No students")
            else MyResult.Success(list)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun getStudent(id: String): MyResult<Student> {
        return try {
            val student = studentsRepo.getStudent(id)
            return if (student == null) MyResult.Failure(msg = "Student not found")
            else MyResult.Success(student)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    //TODO: add to routing & tests, and make it with multiple fields
    suspend fun searchStudents(student: Student): MyResult<List<Student>> {
        return try {
            val list = studentsRepo.searchForStudents(student)
            return if (list.isEmpty()) MyResult.Failure(msg = "Students not found")
            else MyResult.Success(list)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun addStudent(student: Student): MyResult<Unit> {
        return try {
            val acknowledged = studentsRepo.addStudent(student).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun updateStudent(student: Student): MyResult<Unit> {
        return try {
            val acknowledged = studentsRepo.updateStudent(student).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun deleteStudent(id: String): MyResult<Unit> {
        return try {
            val result = studentsRepo.deleteStudent(id).wasAcknowledged()
            if (result) MyResult.Success(Unit) else MyResult.Failure(msg = "Delete failed")
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }


    suspend fun getAllTeachers() = teachersRepo.getAllTeachers()
    suspend fun getTeacher(id: String) = teachersRepo.getTeacher(id)
    suspend fun addTeacher(teacher: Teacher) = teachersRepo.addTeacher(teacher).wasAcknowledged()
    suspend fun deleteTeacher(id: String) = teachersRepo.deleteTeacher(id).wasAcknowledged()


    suspend fun getAllHistories() = historyRepo.getAllHistories()
    suspend fun getHistory(id: String) = historyRepo.getHistory(id)
    suspend fun addHistory(history: History) = historyRepo.addHistory(history).wasAcknowledged()
    suspend fun deleteHistory(id: String) = historyRepo.deleteHistory(id).wasAcknowledged()

}
