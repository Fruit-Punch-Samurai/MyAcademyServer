package db.repos

import kodein
import models.*
import org.kodein.di.generic.instance
import utils.DateManager
import utils.sealed.MyResult

class MainRepo {

    private val pUsersRepo: PrivateUsersRepo by kodein.instance()
    private val usersRepo: UsersRepo by kodein.instance()
    private val studentsRepo: StudentsRepo by kodein.instance()
    private val teachersRepo: TeachersRepo by kodein.instance()
    private val historyRepo: HistoryRepo by kodein.instance()

    //region Users
    suspend fun getAllUsers(): MyResult<List<User>> {
        return try {
            val list = usersRepo.getAllUsers()
            MyResult.Success(list)
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
            val user = usersRepo.deleteUser(id)
            if (user != null) MyResult.Success(Unit) else MyResult.Failure(msg = "Delete failed")
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    //endregion

    //region PrivateUsers
    suspend fun getAllPrivateUsers(): MyResult<List<PrivateUser>> {
        return try {
            val list = pUsersRepo.getAllPrivateUsers()
            MyResult.Success(list)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun getAllAdminPrivateUsers(): MyResult<List<PrivateUser>> {
        return try {
            val list = pUsersRepo.getAllAdminPrivateUsers()
            MyResult.Success(list)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun getPrivateUser(id: String): MyResult<PrivateUser> {
        return try {
            val privateUser = pUsersRepo.getPrivateUser(id)
            return if (privateUser == null) MyResult.Failure(msg = "User not found")
            else MyResult.Success(privateUser)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun getPrivateUser(cred: Credentials): MyResult<PrivateUser> {
        return try {
            val privateUser = pUsersRepo.getPrivateUser(cred)
            return if (privateUser == null) MyResult.Failure(msg = "User not found")
            else MyResult.Success(privateUser)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun addPrivateUser(privateUser: PrivateUser): MyResult<Unit> {
        return try {
            val acknowledged = pUsersRepo.addPrivateUser(privateUser).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun updatePrivateUser(privateUser: PrivateUser): MyResult<Unit> {
        return try {
            val acknowledged = pUsersRepo.updatePrivateUser(privateUser).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun deletePrivateUser(id: String): MyResult<Unit> {
        return try {
            val privateUser = pUsersRepo.deletePrivateUser(id)
            if (privateUser != null) MyResult.Success(Unit) else MyResult.Failure(msg = "Delete failed")
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    //endregion

    //region Students
    suspend fun getAllStudents(): MyResult<List<Student>> {
        return try {
            val list = studentsRepo.getAllStudents()
            MyResult.Success(list)
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

    suspend fun searchStudents(student: Student): MyResult<List<Student>> {
        return try {
            val list = studentsRepo.searchForStudents(student)
            MyResult.Success(list)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun addStudent(student: Student): MyResult<String> {
        return try {
            val id = studentsRepo.addStudent(student.apply {
                date = DateManager.getCurrentLocalDate()
            }).insertedId?.asObjectId()?.value
            id?.let { MyResult.Success(id.toString()) } ?: MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun updateStudent(student: Student): MyResult<Unit> {
        return try {
            val modifiedCount = studentsRepo.updateStudent(student).modifiedCount
            if (modifiedCount == 1L) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun deleteStudent(id: String): MyResult<Unit> {
        return try {
            val student = studentsRepo.deleteStudent(id)
            if (student != null) MyResult.Success(Unit) else MyResult.Failure(msg = "Delete failed")
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    //endregion

    //region Teachers
    suspend fun getAllTeachers(): MyResult<List<Teacher>> {
        return try {
            val list = teachersRepo.getAllTeachers()
            MyResult.Success(list)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun getTeacher(id: String): MyResult<Teacher> {
        return try {
            val teacher = teachersRepo.getTeacher(id)
            return if (teacher == null) MyResult.Failure(msg = "Teacher not found")
            else MyResult.Success(teacher)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun searchTeachers(teacher: Teacher): MyResult<List<Teacher>> {
        return try {
            val list = teachersRepo.searchForTeachers(teacher)
            MyResult.Success(list)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun addTeacher(teacher: Teacher): MyResult<String> {
        return try {
            val id = teachersRepo.addTeacher(teacher.apply {
                date = DateManager.getCurrentLocalDate()
            }).insertedId?.asObjectId()?.value
            id?.let { MyResult.Success(id.toString()) } ?: MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun updateTeacher(teacher: Teacher): MyResult<Unit> {
        return try {
            val modifiedCount = teachersRepo.updateTeacher(teacher).modifiedCount
            if (modifiedCount == 1L) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun deleteTeacher(id: String): MyResult<Unit> {
        return try {
            val teacher = teachersRepo.deleteTeacher(id)
            if (teacher != null) MyResult.Success(Unit) else MyResult.Failure(msg = "Delete failed")
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    //endregion

    //region History
    suspend fun getAllHistories(): MyResult<List<History>> {
        return try {
            val result = historyRepo.getAllHistories()
            MyResult.Success(result)
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun getHistory(id: String): MyResult<History> {
        return try {
            val result = historyRepo.getHistory(id)
            result?.let { MyResult.Success(it) } ?: MyResult.Failure(msg = "History not found")
        } catch (e: Exception) {
            MyResult.Failure(e)
        }

    }

    suspend fun addHistory(history: History): MyResult<String> {
        return try {
            val id = historyRepo.addHistory(history.apply {
                date = DateManager.getCurrentLocalDate()
            }).insertedId?.asObjectId()?.value
            id?.let { MyResult.Success(id.toString()) } ?: MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }
    //endregion

}