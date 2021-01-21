package test.integrationTests

import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.Assert.assertEquals
import main.entity.Story
import main.main
import org.junit.Test
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class ApplicationShould {
    private val mongoConnectionString = "mongodb+srv://admin:admin@cluster0.jddkh.mongodb.net/test"
    private val databaseName = "story-tracker"
    private val collectionName = "stories"

    private val client = KMongo.createClient(mongoConnectionString)
    private val database = client.getDatabase(databaseName)
    private val col = database.getCollection<Story>(collectionName)

    private val writeStory = "{\"id\":6,\"title\":\"title\",\"description\":\"description\",\"assignedTo\":\"assignedTo\"}"
    private val readStory = "{\"id\":7,\"title\":\"title\",\"description\":\"description\",\"assignedTo\":\"assignedTo\"}"

    @Test
    fun returnExpectedStory() {
        withTestApplication({ main(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/v1/story/7").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(readStory, response.content)
            }
        }
    }

    @Test
    fun addStoryPassedToEndpoint() {
        dataCleanup()
        withTestApplication({ main(testing = true) }) {
            handleRequest(HttpMethod.Post, "/api/v1/story"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(writeStory)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Story Added", response.content)
            }
        }
    }

    private fun dataCleanup()
    {
        col.findOneAndDelete(Story::id eq 6)
    }
}