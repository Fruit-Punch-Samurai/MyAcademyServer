package db.repos

import com.mongodb.client.model.Filters
import db.constants.DBConstants
import kodein
import models.Student
import org.bson.types.ObjectId
import org.kodein.di.generic.instance
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class StudentsRepo {

    private val db: CoroutineDatabase by kodein.instance()
    private val col = db.getCollection<Student>(DBConstants.STUDENTS_COL_NAME)

    suspend fun getAllStudents() = col.find().toList()

    suspend fun getStudent(id: String) = col.findOneById(ObjectId(id))

    suspend fun searchForStudents(student: Student): List<Student> {
        return col.find(
            Filters.regex(
                Student::name.name,
                ".*${student.name}.*",
                "i"
            )
        ).toList()
    }

    suspend fun addStudent(student: Student) = col.insertOne(student)

    suspend fun updateStudent(student: Student) = col.updateOne(student)

    suspend fun deleteStudent(student: Student) = deleteStudent(student._id.toString())

    suspend fun deleteStudent(id: String) = col.deleteOne(Student::_id eq ObjectId(id).toId())

}