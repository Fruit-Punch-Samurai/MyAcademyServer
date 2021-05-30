package db.repos

import kodein
import models.*
import org.kodein.di.generic.instance

class MainRepo {

    //TODO: Sealed class for Result, can be success or failure
    //TODO: Make little Repos private + DI

    private val pUsersRepo: PrivateUsersRepo by kodein.instance()
    private val usersRepo: UsersRepo by kodein.instance()
    private val studentsRepo: StudentsRepo by kodein.instance()
    private val teachersRepo: TeachersRepo by kodein.instance()
    private val historyRepo: HistoryRepo by kodein.instance()

    suspend fun getAllUsers() = usersRepo.getAllUsers()
    suspend fun getUser(id: String) = usersRepo.getUser(id)
    suspend fun addUser(user: User) = usersRepo.addUser(user).wasAcknowledged()
    suspend fun deleteUser(id: String) = usersRepo.deleteUser(id).wasAcknowledged()

    suspend fun getAllPrivateUsers() = pUsersRepo.getAllPrivateUsers()
    suspend fun getPrivateUser(id: String) = pUsersRepo.getPrivateUser(id)
    suspend fun addPrivateUser(privateUser: PrivateUser) = pUsersRepo.addPrivateUser(privateUser).wasAcknowledged()
    suspend fun deletePrivateUser(id: String) = pUsersRepo.deletePrivateUser(id).wasAcknowledged()
    suspend fun getPrivateUser(name: String, pass: String) = pUsersRepo.getPrivateUser(name, pass)

    suspend fun getAllStudents() = studentsRepo.getAllStudents()
    suspend fun getStudent(id: String) = studentsRepo.getStudent(id)
    suspend fun addStudent(student: Student) = studentsRepo.addStudent(student).wasAcknowledged()
    suspend fun deleteStudent(id: String) = studentsRepo.deleteStudent(id).wasAcknowledged()

    suspend fun getAllTeachers() = teachersRepo.getAllTeachers()
    suspend fun addTeacher(teacher: Teacher) = teachersRepo.addTeacher(teacher)

    suspend fun getAllHistories() = historyRepo.getAllHistories()
    suspend fun addHistory(history: History) = historyRepo.addHistory(history)


}