package db.repos

import db.constants.DBConstants
import kodein
import models.Teacher
import org.kodein.di.generic.instance
import org.litote.kmongo.coroutine.CoroutineDatabase

class TeachersRepo {

    private val db: CoroutineDatabase by kodein.instance()
    private val col = db.getCollection<Teacher>(DBConstants.TEACHERS_COL_NAME)

    suspend fun getAllTeachers() = col.find().toList()

    suspend fun addTeacher(teacher: Teacher) {
        col.insertOne(teacher)
    }


}