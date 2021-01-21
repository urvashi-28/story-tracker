package test.integrationTests

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.Assert.assertEquals
import main.entity.Story
import main.main
import org.junit.Test
import java.io.BufferedReader
import java.io.File

class ApplicationShould {

    private val filePath = "src/main/db/db.json"
    private var gson = Gson()
    private val file= File(filePath)

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
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        var storyList =  gson.fromJson(inputString, Array<Story>::class.java).toMutableList()
        storyList.removeAll { it.id == 6 }
        var jsonString:String = gson.toJson(storyList)
        file.writeText(jsonString)
    }
}