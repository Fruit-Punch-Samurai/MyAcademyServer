package utils

import db.constants.DBConstants
import db.repos.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object DependencyInjection {

    fun inject(): DI {
        return DI {
            bind<CoroutineDatabase>() with singleton { KMongo.createClient().coroutine.getDatabase(DBConstants.DB_NAME) }
            bind<UsersRepo>() with singleton { UsersRepo() }
            bind<PrivateUsersRepo>() with singleton { PrivateUsersRepo() }
            bind<TeachersRepo>() with singleton { TeachersRepo() }
            bind<StudentsRepo>() with singleton { StudentsRepo() }
            bind<PaymentsRepo>() with singleton { PaymentsRepo() }
            bind<HistoryRepo>() with singleton { HistoryRepo() }
            bind<MainRepo>() with singleton { MainRepo() }
        }
    }

}