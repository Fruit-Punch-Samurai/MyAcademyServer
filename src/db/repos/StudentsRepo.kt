package db.repos

import com.mongodb.client.model.Filters
import db.constants.DBConstants
import kodein
import models.Student
import org.bson.types.ObjectId
import org.kodein.di.generic.instance
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class StudentsRepo {

    private val db: CoroutineDatabase by kodein.instance()
    private val col = db.getCollection<Student>(DBConstants.STUDENTS_COL_NAME)

    suspend fun getAllStudents() = col.find().toList()

    //TODO: Sort by Date
    suspend fun getLastStudents() = col.find().limit(100).toList()

    suspend fun getStudent(id: String) = col.findOneById(ObjectId(id))

    suspend fun searchForStudents(student: Student): List<Student> {
        return col.find(
            student.name?.let {
                Filters.regex(
                    Student::name.name,
                    ".*${it}.*",
                    "i"
                )
            }, student.firstName?.let {
                Filters.regex(
                    Student::firstName.name,
                    ".*${it}.*",
                    "i"
                )
            }, student.phone?.let {
                Filters.regex(
                    Student::phone.name,
                    ".*${it}.*",
                    "i"
                )
            }
        ).toList()
    }

    suspend fun addStudent(student: Student) = col.insertOne(student)

    suspend fun updateStudent(student: Student) = col.updateOne(Student::_id eq student._id, student)

    suspend fun deleteStudent(student: Student) = deleteStudent(student._id.toString())

    suspend fun deleteStudent(id: String) = col.findOneAndDelete(Student::_id eq ObjectId(id).toId())

}