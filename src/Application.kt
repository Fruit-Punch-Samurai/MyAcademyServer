import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import server.Features.setupFeatures
import server.routes.MainRouting.setupRoutes
import utils.DependencyInjection

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

//TODO: Remove development mode on production
//TODO: Custom port to fool scanners
//TODO: No weak passwords
//TODO: Change hashing algo && reverse and ROT
//TODO: Hide response headers
//TODO: Fix timezone issues || use $current_date
//TODO: UI side: option to enter DB credentials in app, and encrypt them with SerialID
//TODO: SSL for HTTP/2, HTTPS maybe?
//TODO: Block external headers

val kodein = DependencyInjection.inject()

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    HttpClient(Jetty) {
        setupFeatures()
        setupRoutes()
    }

}