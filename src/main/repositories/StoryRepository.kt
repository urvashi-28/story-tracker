package main.repositories

import com.google.gson.Gson
import main.entity.Story
import java.io.BufferedReader
import java.io.File

class StoryRepository(val filePath: String) {

    private var gson = Gson()
    private val file= File(filePath)

    fun writeStoryDetails(story: Story): String
    {
        var stories = getAllStories()
        for (item in stories){
            if(item.id == story.id)
                return "Story already Exist"
        }

        stories.add(story)
        var jsonString:String = gson.toJson(stories)
        file.writeText(jsonString)

        return "Story Added"
    }

    fun readStoryDetails(id: Int): Story?
    {
        var stories = getAllStories()
        for (story in stories){
            if(story.id == id)
            {
                return story
            }
        }

        return null
    }

    private fun getAllStories(): MutableList<Story> {
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        return gson.fromJson(inputString, Array<Story>::class.java).toMutableList()
    }
}