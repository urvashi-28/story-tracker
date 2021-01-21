package main.services

import main.repositories.StoryRepository
import org.kodein.di.*

fun bind(): DirectDI {
    return DI {
        bind<StoryRepository>() with singleton { StoryRepository("mongodb+srv://admin:admin@cluster0.jddkh.mongodb.net/test", "story-tracker", "stories") }
        bind<StoryService>() with singleton { StoryService(instance()) }
    }.direct
}