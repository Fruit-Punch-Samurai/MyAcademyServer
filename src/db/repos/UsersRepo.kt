package db.repos

import db.constants.DBConstants
import kodein
import models.User
import org.bson.types.ObjectId
import org.kodein.di.instance
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class UsersRepo {

    private val db: CoroutineDatabase by kodein.instance()
    private val col = db.getCollection<User>(DBConstants.USERS_COL_NAME)

    suspend fun getAllUsers() = col.find().toList()

    suspend fun getUser(id: String) = col.findOneById(ObjectId(id))

    suspend fun addUser(user: User) = col.insertOne(user)

    suspend fun deleteUser(user: User) = deleteUser(user._id.toString())

    suspend fun deleteUser(id: String) = col.findOneAndDelete(User::_id eq ObjectId(id).toId())


}