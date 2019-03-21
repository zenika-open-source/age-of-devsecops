package com.zenika.quiz.application

import assertk.assert
import assertk.assertions.isEqualTo
import com.zenika.quiz.domain.Quiz
import com.zenika.quiz.domain.QuizRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test

class QuizFetcherTest {

    @Test
    fun `should fetch a quiz by id`() {
        val quiz = Quiz("1", "", "", emptyList(), emptyList(), "")
        val quizRepository = mockk<QuizRepository>()
        every { quizRepository.get(quiz.id) } returns quiz
        val quizFetcher = QuizFetcher(quizRepository)

        val fetched = quizFetcher.byId(quiz.id)

        assert(fetched).isEqualTo(quiz)
    }
}
