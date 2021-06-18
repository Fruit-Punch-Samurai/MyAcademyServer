package server.security

import db.repos.MainRepo
import io.ktor.auth.*
import kodein
import org.kodein.di.generic.instance
import server.security.SecurityConstants.REALM
import server.security.SecurityConstants.REGULAR_USER_AUTH
import utils.sealed.MyResult
import java.security.MessageDigest

object Auth {

    private val repo: MainRepo by kodein.instance()

    private fun getMd5Digest(str: String): ByteArray =
        MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

    suspend fun Authentication.Configuration.setupRegularUserAuth() {
        var usersList = emptyMap<String, ByteArray>()
        val result = repo.getAllPrivateUsers()

        if (result is MyResult.Success) {
            usersList = result.value.associate { it.name to getMd5Digest("${it.name}:$REALM:${it.password}") }
        }

        digest(REGULAR_USER_AUTH) {
            realm = REALM
            digestProvider { userName, _ ->
                usersList[userName]
            }
        }
    }

}