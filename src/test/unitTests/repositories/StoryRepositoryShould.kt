package test.unitTests.repositories

import main.entity.Story
import main.repositories.StoryRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection


class StoryRepositoryShould {
    private val mongoConnectionString = "mongodb+srv://admin:admin@cluster0.jddkh.mongodb.net/test"
    private val databaseName = "story-tracker"
    private val collectionName = "stories"

    private val client = KMongo.createClient(mongoConnectionString)
    private val database = client.getDatabase(databaseName)
    private val col = database.getCollection<Story>(collectionName)

    private val storyRepository = StoryRepository(mongoConnectionString, databaseName, collectionName)
    private val story1Expected = Story(1111, "StoryTitle", "Description", "Assigned To")
    private val story2Expected = Story(2222, "StoryTitle", "Description", "Assigned To")

    @Before
    fun setup()
    {
        col.insertOne(story2Expected)
    }

    @Test
    fun `writeStoryDetailsWhenWriteStoryDetailsIsCalled`()
    {
        assertEquals(storyRepository.writeStoryDetails(story1Expected), "Story Added")
        val storyAdded = getStoryByStoryId(story1Expected.id)
        assertEquals(story1Expected, storyAdded)
    }

    @Test
    fun `update Story Details When Update Story Details Is Called`()
    {
        storyRepository.writeStoryDetails(story1Expected)
        story1Expected.description = "new description"
        assertEquals(storyRepository.updateStoryDetails(story1Expected), "Story Updated")
        val storyAdded = getStoryByStoryId(story1Expected.id)
        assertEquals(story1Expected, storyAdded)
    }

    @Test
    fun `update Story Assignment When Update Story Assignment Is Called`()
    {
        storyRepository.writeStoryDetails(story1Expected)
        story1Expected.assignedTo = "new assign"
        assertEquals(storyRepository.updateStoryAssignment(story1Expected.id, story1Expected.assignedTo), "AssignedTo updated in Story")
        val storyAdded = getStoryByStoryId(story1Expected.id)
        assertEquals(story1Expected.assignedTo, "new assign")
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
        col.findOneAndDelete(Story::id eq story1Expected.id)
        col.findOneAndDelete(Story::id eq story2Expected.id)
    }

    private fun getStoryByStoryId(id: Int): Story?
    {
        val story = col.find(Story::id eq id).first()
        if(story != null)
            return story

        return null
    }
}