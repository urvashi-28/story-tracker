package main.routes

import io.ktor.routing.*
import org.kodein.di.DirectDI

fun Routing.apiRoute(dependencies: DirectDI) {
    route("/api/v1") {
        story(dependencies)
    }
}