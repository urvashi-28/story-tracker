package main.repositories

import com.mongodb.client.MongoCollection
import main.entity.Story
import org.litote.kmongo.*

class StoryRepository {
    private var col : MongoCollection<Story>;

    constructor(mongoConnectionString: String, databaseName: String, collectionName: String){
        val client = KMongo.createClient(mongoConnectionString)
        val database = client.getDatabase(databaseName)
        col = database.getCollection<Story>(collectionName)
    }

    fun writeStoryDetails(story: Story): String
    {
        val existingStory : Story? = col.findOne(Story::id eq story.id)
        if (existingStory != null)
        {
            return "Story already Exist"
        }

        col.insertOne(story)
        return "Story Added"
    }

    fun updateStoryDetails(story: Story): String
    {
        col.updateOne(Story::id eq story.id, set(
            Story::assignedTo setTo story.assignedTo,
            Story::title setTo story.title,
            Story::description setTo story.description
        ))

        return "Story Updated"
    }

    fun updateStoryAssignment(storyId: Int, assignTo: String): String
    {
//        val story : Story = col.findOne(Story::id eq storyId) ?: return "story does not exist"
        col.updateOne(Story::id eq storyId, setValue(Story::assignedTo, assignTo))

        return "AssignedTo updated in Story"
    }

    fun readStoryDetails(id: Int): Story?
    {
        val story : Story? = col.findOne(Story::id eq id)
        if (story != null)
        {
            return story
        }

        return null
    }
}