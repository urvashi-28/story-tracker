package main

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import main.routes.apiRoute
import main.services.bind

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) { gson { } }
    install(CallLogging)

    val dependencies = bind()

    routing {
        apiRoute(dependencies)
    }
}