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

    suspend fun getAllUsers() = usersRepo.getAllUsers()
    suspend fun getUser(id: String) = usersRepo.getUser(id)
    suspend fun addUser(user: User): MyResult<Unit> {
        return try {
            val acknowledged = usersRepo.addUser(user).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun deleteUser(id: String) = usersRepo.deleteUser(id).wasAcknowledged()

    suspend fun getAllPrivateUsers() = pUsersRepo.getAllPrivateUsers()
    suspend fun getPrivateUser(id: String) = pUsersRepo.getPrivateUser(id)
    suspend fun addPrivateUser(privateUser: PrivateUser) = pUsersRepo.addPrivateUser(privateUser).wasAcknowledged()
    suspend fun deletePrivateUser(id: String) = pUsersRepo.deletePrivateUser(id).wasAcknowledged()
    suspend fun getPrivateUser(name: String, pass: String) = pUsersRepo.getPrivateUser(name, pass)

    suspend fun getAllStudents() = studentsRepo.getAllStudents()
    suspend fun getStudent(id: String) = studentsRepo.getStudent(id)
    suspend fun addStudent(student: Student): MyResult<Unit> {
        return try {
            val acknowledged = studentsRepo.addStudent(student).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun deleteStudent(id: String) = studentsRepo.deleteStudent(id).wasAcknowledged()

    suspend fun getAllTeachers() = teachersRepo.getAllTeachers()
    suspend fun getTeacher(id: String) = teachersRepo.getTeacher(id)
    suspend fun addTeacher(teacher: Teacher) = teachersRepo.addTeacher(teacher).wasAcknowledged()
    suspend fun deleteTeacher(id: String) = teachersRepo.deleteTeacher(id).wasAcknowledged()

    suspend fun getAllHistories() = historyRepo.getAllHistories()
    suspend fun getHistory(id: String) = historyRepo.getHistory(id)
    suspend fun addHistory(history: History) = historyRepo.addHistory(history).wasAcknowledged()
    suspend fun deleteHistory(id: String) = historyRepo.deleteHistory(id).wasAcknowledged()

}