package test.unitTests.services

import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import main.entity.Story
import main.repositories.StoryRepository
import main.services.StoryService
import org.junit.Before
import org.junit.Test

class StoryServiceShould {
    private lateinit var storyRepositoryMock: StoryRepository
    private lateinit var storyService: StoryService
    private lateinit var story: Story

    @Before
    fun setup()
    {
        storyRepositoryMock = mockk<StoryRepository>(relaxed = true)
        storyService = StoryService(storyRepositoryMock)
        story = Story(1, "title", "desc", "assigned to", "Backlog")
    }

    @Test
    fun writeStoryDetailsWhenWriteStoryDetailsIsCalled()
    {
        every { storyRepositoryMock.writeStoryDetails(any()) } returns "Returned String"
        var result = storyService.addStory(story)
        assertEquals("Returned String", result)
    }

    @Test
    fun readStoryDetailsWhenReadStoryDetailsIsCalled()
    {
        every { storyRepositoryMock.readStoryDetails(1) } returns story
        var result = storyService.getStory(1)
        assertEquals(story, result)
    }
}