package db.repos

import db.constants.DBConstants
import kodein
import models.Teacher
import org.bson.types.ObjectId
import org.kodein.di.generic.instance
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class TeachersRepo {

    private val db: CoroutineDatabase by kodein.instance()
    private val col = db.getCollection<Teacher>(DBConstants.TEACHERS_COL_NAME)

    suspend fun getAllTeachers() = col.find().toList()

    suspend fun getTeacher(id: String) = col.findOneById(ObjectId(id))

    suspend fun addTeacher(teacher: Teacher) = col.insertOne(teacher)

    suspend fun deleteTeacher(teacher: Teacher) = deleteTeacher(teacher._id.toString())

    suspend fun deleteTeacher(id: String) = col.deleteOne(Teacher::_id eq ObjectId(id).toId())


}