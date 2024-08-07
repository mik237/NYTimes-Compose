package me.ibrahim.nytimes.domain.usecases

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.domain.repositories.NYTimesRepository
import org.junit.Before
import kotlin.test.Test


class GetTopStoriesUseCaseTest {

    private lateinit var nyTimesRepository: NYTimesRepository
    private lateinit var getTopStoriesUseCase: GetTopStoriesUseCase

    @Before
    fun setUp() {
        nyTimesRepository = mockk()
        getTopStoriesUseCase = GetTopStoriesUseCase(nyTimesRepository)
    }


    @Test
    fun `invoke returns success response`() = runBlocking {
        // Given
        val type = "arts"
        val successResponse = NetworkResponse.Success(listOf("Story 1", "Story 2"))
        coEvery { nyTimesRepository.getTopStories(type) } returns flowOf(successResponse)

        // When
        val result = mutableListOf<NetworkResponse>()
        getTopStoriesUseCase(type).collect { response ->
            result.add(response)
        }

        // Then
        assertEquals(1, result.size)
        assertEquals(successResponse, result[0])
    }


    @Test
    fun `invoke returns error response`() = runBlocking {
        // Given
        val type = "arts"
        val errorResponse = NetworkResponse.Error("Network Error")
        coEvery { nyTimesRepository.getTopStories(type) } returns flowOf(errorResponse)

        // When
        val result = mutableListOf<NetworkResponse>()
        getTopStoriesUseCase(type).collect { response ->
            result.add(response)
        }

        // Then
        assertEquals(1, result.size)
        assertEquals(errorResponse, result[0])
    }
}