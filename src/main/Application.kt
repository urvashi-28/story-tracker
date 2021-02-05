package main

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.routing.*
import main.routes.apiRoute
import main.services.bind

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.main(testing: Boolean = false) {

    install(ContentNegotiation) { gson { } }
    install(CallLogging)
    install(CORS){
        method(HttpMethod.Options)
        header(HttpHeaders.XForwardedProto)
        anyHost()
        host("localhost:8080")
        allowCredentials = true
        allowNonSimpleContentTypes = true
        anyHost()
    }

    val dependencies = bind()

    routing {
        apiRoute(dependencies)
    }
}