
import io.ktor.application.*
import io.ktor.server.jetty.*
import server.Plugins.setupFeatures
import server.routes.MainRouting.setupRoutes
import utils.DependencyInjection

fun main(args: Array<String>): Unit = EngineMain.main(args)

//TODO: Remove development mode on production
//TODO: Custom port to fool scanners
//TODO: No weak passwords
//TODO: Change hashing algo && reverse and ROT
//TODO: Hide response headers
//TODO: UI side: option to enter DB credentials in app, and encrypt them with SerialID
//TODO: SSL for HTTP/2, HTTPS maybe?
//TODO: Block external headers

val kodein = DependencyInjection.inject()

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
        setupFeatures()
        setupRoutes()
}