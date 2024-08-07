package me.ibrahim.nytimes.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.domain.models.UiState
import me.ibrahim.nytimes.domain.usecases.GetTopStoriesUseCase
import me.ibrahim.nytimes.domain.usecases.SaveTopStoryUseCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.test.Test


@ExperimentalCoroutinesApi
class NYTimesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var getTopStoriesUseCase: GetTopStoriesUseCase
    private lateinit var saveTopStoryUseCase: SaveTopStoryUseCase
    private lateinit var viewModel: NYTimesViewModel

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        getTopStoriesUseCase = mockk()
        saveTopStoryUseCase = mockk()
        viewModel = NYTimesViewModel(getTopStoriesUseCase, saveTopStoryUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cancel()
    }

    @Test
    fun `fetchTopStories updates state with success response`() {
        // Given
        val stories = listOf(
            TopStory(
                title = "‘House of the Dragon’: 5 Questions as We Look Ahead to Season 3",
                description = "result.abstract",
                byline = "By Jennifer Vineyard",
                createdDate = "result.createdDate",
                itemType = "result.itemType",
                caption = "result.multimedia?.get(0)?.caption",
                largeImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-superJumbo.jpg",
                smallImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-thumbLarge.jpg",
                publishedDate = "result.publishedDate",
                section = "result.section",
                shortUrl = "result.shortUrl",
                subsection = "result.subsection",
                updatedDate = "result.updatedDate",
                url = "result.url"
            )
        )

        // Given
        val type = "arts"
        val successResponse = NetworkResponse.Success(stories)
        coEvery { getTopStoriesUseCase.invoke(type) } returns flowOf(successResponse)
        // When
        viewModel.fetchTopStories()

        // Then
        dispatcher.scheduler.advanceUntilIdle() // Ensure all coroutines complete

        assertEquals(UiState.Success, viewModel.state.value.uiState)
    }

    @Test
    fun `fetchTopStories updates state with error response`() = runBlocking {

        // Given
        val type = "arts"
        val errorResponse = NetworkResponse.Error("An error occurred")
        coEvery { getTopStoriesUseCase.invoke(type) } returns flowOf(errorResponse)

        // When
        viewModel.fetchTopStories()

        // Then
        dispatcher.scheduler.advanceUntilIdle() // Ensure all coroutines complete

        assertEquals(UiState.Error("An error occurred"), viewModel.state.value.uiState)
    }

    @Test
    fun `searchStory updates searchQuery`() {
        // Given
        val query = "New Query"

        // When
        viewModel.onEvent(NYTimesEvents.SearchStory(query))

        // Then
        assertEquals(query, viewModel.searchQuery.value)
    }


    @Test
    fun `storyClicked updates selectedStory and calls saveTopStoryUseCase`() {
        // Given
        val story = TopStory(
            title = "‘House of the Dragon’: 5 Questions as We Look Ahead to Season 3",
            description = "result.abstract",
            byline = "By Jennifer Vineyard",
            createdDate = "result.createdDate",
            itemType = "result.itemType",
            caption = "result.multimedia?.get(0)?.caption" ?: "",
            largeImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-superJumbo.jpg",
            smallImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-thumbLarge.jpg",
            publishedDate = "result.publishedDate",
            section = "result.section",
            shortUrl = "result.shortUrl",
            subsection = "result.subsection",
            updatedDate = "result.updatedDate",
            url = "result.url"
        )

        // When
        viewModel.onEvent(NYTimesEvents.StoryClicked(story))

        // Then
        assertEquals(story, viewModel.state.value.selectedStory)
        coVerify { saveTopStoryUseCase(story) }
    }


    @Test
    fun `filterSection updates selectedSection and filteredStories`() {
        // Given
        val stories = listOf(
            TopStory(
                title = "‘House of the Dragon’: 5 Questions as We Look Ahead to Season 3",
                description = "result.abstract",
                byline = "By Jennifer Vineyard",
                createdDate = "result.createdDate",
                itemType = "result.itemType",
                caption = "result.multimedia?.get(0)?.caption" ?: "",
                largeImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-superJumbo.jpg",
                smallImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-thumbLarge.jpg",
                publishedDate = "result.publishedDate",
                section = "Science",
                shortUrl = "result.shortUrl",
                subsection = "result.subsection",
                updatedDate = "result.updatedDate",
                url = "result.url"
            )
        )

        // Given
        val type = "arts"
        val successResponse = NetworkResponse.Success(stories)
        coEvery { getTopStoriesUseCase.invoke(type) } returns flowOf(successResponse)

        // When
        viewModel.fetchTopStories()

        dispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.onEvent(NYTimesEvents.FilterSection("Science"))


        // Then
        assertEquals("Science", viewModel.state.value.selectedSection)

        assertEquals(
            listOf(
                TopStory(
                    title = "‘House of the Dragon’: 5 Questions as We Look Ahead to Season 3",
                    description = "result.abstract",
                    byline = "By Jennifer Vineyard",
                    createdDate = "result.createdDate",
                    itemType = "result.itemType",
                    caption = "result.multimedia?.get(0)?.caption" ?: "",
                    largeImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-superJumbo.jpg",
                    smallImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-thumbLarge.jpg",
                    publishedDate = "result.publishedDate",
                    section = "Science",
                    shortUrl = "result.shortUrl",
                    subsection = "result.subsection",
                    updatedDate = "result.updatedDate",
                    url = "result.url"
                )
            ), viewModel.state.value.filteredStories
        )
    }

    @Test
    fun `scrollPosition updates scrollPosition`() {
        // Given
        val position = 10

        // When
        viewModel.onEvent(NYTimesEvents.ScrollPosition(position))

        // Then
        assertEquals(position, viewModel.scrollPosition.value)
    }


    // TestCoroutineRule class definition
    class TestCoroutineRule : TestWatcher() {
        override fun starting(description: Description) {
            super.starting(description)
            Dispatchers.setMain(StandardTestDispatcher())
        }

        override fun finished(description: Description) {
            super.finished(description)
            Dispatchers.resetMain()
        }
    }
}