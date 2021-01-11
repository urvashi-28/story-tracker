package main.services

import main.entity.Story
import main.repositories.StoryRepository

class StoryService(private val storyRepository: StoryRepository){

    fun getStory(id: Int): Story? {
        return storyRepository.readStoryDetails(id)
    }

    fun addStory(story: Story): String {
        return storyRepository.writeStoryDetails(story)
    }
}