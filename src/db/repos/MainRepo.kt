package db.repos

import kodein
import models.*
import org.kodein.di.generic.instance
import utils.sealed.MyResult

class MainRepo {

    //TODO: Make little Repos private + DI
    //TODO: Delete requests return true even if Entity doesn't exist
    //TODO: Updating my need to be changed

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
            val user = usersRepo.deleteUser(id)
            if (user != null) MyResult.Success(Unit) else MyResult.Failure(msg = "Delete failed")
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }


    suspend fun getAllPrivateUsers(): MyResult<List<PrivateUser>> {
        return try {
            val list = pUsersRepo.getAllPrivateUsers()
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


    suspend fun getAllTeachers(): MyResult<List<Teacher>> {
        return try {
            val list = teachersRepo.getAllTeachers()
            if (list.isEmpty()) MyResult.Failure(msg = "No teachers")
            else MyResult.Success(list)
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

    suspend fun addTeacher(teacher: Teacher): MyResult<Unit> {
        return try {
            val acknowledged = teachersRepo.addTeacher(teacher).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
        } catch (e: Exception) {
            MyResult.Failure(e)
        }
    }

    suspend fun updateTeacher(teacher: Teacher): MyResult<Unit> {
        return try {
            val acknowledged = teachersRepo.updateTeacher(teacher).wasAcknowledged()
            if (acknowledged) MyResult.Success(Unit) else MyResult.Failure()
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

    suspend fun getAllHistories() = historyRepo.getAllHistories()
    suspend fun getHistory(id: String) = historyRepo.getHistory(id)
    suspend fun addHistory(history: History) = historyRepo.addHistory(history).wasAcknowledged()

}
