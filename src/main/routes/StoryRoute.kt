package main.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import main.entity.Story
import main.services.StoryService
import org.kodein.di.DirectDI
import org.kodein.di.instance


fun Route.story(dependencies: DirectDI) {

    val storyService: StoryService = dependencies.instance()

    post("/story") {
        try {
            val storyDetails = call.receive<Story>()
            val message = storyService.addStory(storyDetails)

            call.respond(message)
        } catch (e: Exception) {
            call.respond(e)
        }
    }

    patch("/story") {
        try {
            val storyDetails = call.receive<Story>()
            val message = storyService.updateStory(storyDetails)

            call.respond(message)
        } catch (e: Exception) {
            call.respond(e)
        }
    }

    patch("/story/assignTo") {
        try {
            val storyDetails = call.receive<Story>()
            val message = storyService.updateStoryAssignment(storyDetails.id, storyDetails.assignedTo)

            call.respond(message)
        } catch (e: Exception) {
            call.respond(e)
        }
    }

    get("/story/{storyId}") {
        try {
            val id = call.parameters.get("storyId")!!.toInt()
            val story = storyService.getStory(id)

            if (story == null) {
                call.respond("Not Found")
            } else {
                call.respond(story)
            }
        } catch (e: Exception) {
            call.respond(e)
        }
    }
}
