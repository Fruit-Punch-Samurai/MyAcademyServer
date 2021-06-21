package db.repos

import com.mongodb.client.model.Filters
import db.constants.DBConstants
import kodein
import models.Teacher
import org.bson.types.ObjectId
import org.kodein.di.generic.instance
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class TeachersRepo {

    private val db: CoroutineDatabase by kodein.instance()
    private val col = db.getCollection<Teacher>(DBConstants.TEACHERS_COL_NAME)

    suspend fun getAllTeachers() = col.find().toList()

    suspend fun getLastTeachers() = col.find().limit(100).descendingSort(Teacher::date).toList()

    suspend fun getTeacher(id: String) = col.findOneById(ObjectId(id))

    suspend fun searchForTeachers(teacher: Teacher): List<Teacher> {
        return col.find(
            teacher.name?.let {
                Filters.regex(
                    Teacher::name.name,
                    ".*${it}.*",
                    "i"
                )
            }, teacher.firstName?.let {
                Filters.regex(
                    Teacher::firstName.name,
                    ".*${it}.*",
                    "i"
                )
            }, teacher.phone?.let {
                Filters.regex(
                    Teacher::phone.name,
                    ".*${it}.*",
                    "i"
                )
            }
        ).toList()
    }

    suspend fun addTeacher(teacher: Teacher) = col.insertOne(teacher)

    suspend fun updateTeacher(teacher: Teacher) = col.updateOne(teacher)

    suspend fun deleteTeacher(teacher: Teacher) = deleteTeacher(teacher._id.toString())

    suspend fun deleteTeacher(id: String) = col.findOneAndDelete(Teacher::_id eq ObjectId(id).toId())

}