package db.repos

import db.constants.DBConstants
import kodein
import models.Credentials
import models.PrivateUser
import org.bson.types.ObjectId
import org.kodein.di.generic.instance
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class PrivateUsersRepo {

    private val db: CoroutineDatabase by kodein.instance()
    private val col = db.getCollection<PrivateUser>(DBConstants.PRIVATE_USERS_COL_NAME)

    suspend fun getAllPrivateUsers() = col.find().toList()

    suspend fun getPrivateUser(id: String) = col.findOneById(ObjectId(id))

    suspend fun getPrivateUser(name: String, pass: String) =
        col.findOne(Credentials::name eq name, Credentials::password eq pass)

    suspend fun getPrivateUser(cred: Credentials) = getPrivateUser(cred.name, cred.password)

    suspend fun addPrivateUser(privateUser: PrivateUser) = col.insertOne(privateUser)

    suspend fun updatePrivateUser(privateUser: PrivateUser) =
        col.updateOne(PrivateUser::_id eq privateUser._id, privateUser)

    suspend fun deletePrivateUser(privateUser: PrivateUser) = deletePrivateUser(privateUser._id.toString())

    suspend fun deletePrivateUser(id: String) = col.findOneAndDelete(PrivateUser::_id eq ObjectId(id).toId())


}