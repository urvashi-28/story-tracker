package main.services

import main.repositories.StoryRepository
import org.kodein.di.*

fun bind(): DirectDI {
    return DI {
        bind<StoryRepository>() with singleton { StoryRepository("src/main/db/db.json") }
        bind<StoryService>() with singleton { StoryService(instance()) }
    }.direct
}