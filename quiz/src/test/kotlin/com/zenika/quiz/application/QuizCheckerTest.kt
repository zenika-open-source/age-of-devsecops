package com.zenika.quiz.application

import assertk.assert
import assertk.assertions.isEqualTo
import com.zenika.quiz.domain.Quiz
import com.zenika.quiz.domain.QuizRepository
import com.zenika.quiz.domain.ResponseSheet
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test

class QuizCheckerTest {

    @Test
    fun `should fetch quiz and return message with grade when quiz not solved`() {
        val responseSheet = ResponseSheet("", emptyList())
        val responseSheetGrade = 2.toDouble().div(3)
        val quiz = mockk<Quiz>()
        every { quiz.grade(responseSheet) } returns responseSheetGrade
        val quizRepository = mockk<QuizRepository>()
        every { quizRepository.get(quiz.id) } returns quiz
        val quizChecker = QuizChecker(quizRepository)

        val result = quizChecker.check(responseSheet)

        assert(result).isEqualTo("Try again!")
    }

    @Test
    fun `should fetch quiz and return success message when quiz solved`() {
        val responseSheet = ResponseSheet("", emptyList())
        val responseSheetGrade = 1.0
        val successMessage = "Well done! Here is your flag: 1234"
        val quiz = mockk<Quiz>()
        every { quiz.grade(responseSheet) } returns responseSheetGrade
        every { quiz.successMessage } returns successMessage
        val quizRepository = mockk<QuizRepository>()
        every { quizRepository.get(quiz.id) } returns quiz
        val quizChecker = QuizChecker(quizRepository)

        val result = quizChecker.check(responseSheet)

        assert(result).isEqualTo(successMessage)
    }
}
