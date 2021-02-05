package main.services

import main.entity.Story
import main.repositories.StoryRepository

class StoryService(private val storyRepository: StoryRepository){

    fun getStory(id: Int): Story? {
        return storyRepository.readStoryDetails(id)
    }

    fun getAllStories(): List<Story> {
        return storyRepository.readAllStories()
    }

    fun addStory(story: Story): String {
        return storyRepository.writeStoryDetails(story)
    }

    fun updateStory(story: Story): String {
        return storyRepository.updateStoryDetails(story)
    }

    fun updateStoryStatus(storyId: Int, status: String): String {
        return storyRepository.updateStoryStatus(storyId, status)
    }

    fun updateStoryAssignment(storyId: Int, assignTo: String): String {
        return storyRepository.updateStoryAssignment(storyId, assignTo)
    }
}