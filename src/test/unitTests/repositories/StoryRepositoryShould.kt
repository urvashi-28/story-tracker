package test.unitTests.repositories

import com.google.gson.Gson
import main.entity.Story
import main.repositories.StoryRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.File


class StoryRepositoryShould {

    private val filePath = "src/test/db/db.json"
    private var gson = Gson()
    private val file= File(filePath)

    private val storyRepository = StoryRepository(filePath)
    private val story1Expected = Story(1, "StoryTitle", "Description", "Assigned To")
    private val story2Expected = Story(2, "StoryTitle", "Description", "Assigned To")

    @Before
    fun setup()
    {
        var stories = mutableListOf<Story>()
        stories.add(story2Expected)
        var jsonString:String = gson.toJson(stories)
        file.writeText(jsonString)
    }

    @Test
    fun `writeStoryDetailsWhenWriteStoryDetailsIsCalled`()
    {
        assertEquals(storyRepository.writeStoryDetails(story1Expected), "Story Added")
        val storyAdded = getStoryByStoryId(story1Expected.id)
        assertEquals(story1Expected, storyAdded)
    }

    @Test
    fun returnMessageIfStoryDetailsAlreadyExistWhenWriteStoryDetailsIsCalled() {
        assertEquals(storyRepository.writeStoryDetails(story2Expected), "Story already Exist")
    }

    @Test
    fun returnStoryDetailsIfStoryExistsWhenReadStoryDetailsIsCalled() {
        assertEquals(storyRepository.readStoryDetails(story2Expected.id), story2Expected)
    }

    @Test
    fun returnNullIfStoryDoesNotExistWhenReadStoryDetailsIsCalled() {
        assertNull(storyRepository.readStoryDetails(1122))
    }

    @After
    fun teardown()
    {
        var jsonString:String = gson.toJson(listOf<Story>())
        file.writeText(jsonString)
    }

    private fun getStoryByStoryId(id: Int): Story?
    {
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        val stories = gson.fromJson(inputString, Array<Story>::class.java).toMutableList()

        for (story in stories)
        {
            if(story.id == id)
                return story
        }
        return null
    }
}