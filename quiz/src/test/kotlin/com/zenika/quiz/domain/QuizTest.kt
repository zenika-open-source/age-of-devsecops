package com.zenika.quiz.domain

import assertk.assert
import assertk.assertions.isEqualTo
import com.zenika.quiz.domain.Question
import com.zenika.quiz.domain.Quiz
import com.zenika.quiz.domain.Response
import com.zenika.quiz.domain.ResponseSheet
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import kotlin.test.Test

class QuizTest {

    @Test
    fun `a quiz should have a unique identifier`() {
        val id = "myquiz-123"
        val quiz = Quiz(id, "", "", emptyList(), emptyList(), "")

        assert(quiz.title).isEqualTo("")
    }

    @Test
    fun `a quiz should have a title`() {
        val title = "Quiz title"
        val quiz = Quiz("id123", title, "", emptyList(), emptyList(), "")

        assert(quiz.title).isEqualTo(title)
    }

    @Test
    fun `a quiz should have a description`() {
        val description = "What do you know about this awesome topic?"
        val quiz = Quiz("id123", "", description, emptyList(), emptyList(), "")

        assert(quiz.description).isEqualTo(description)
    }

    @Test
    fun `a quiz should optionally provide external links as clues`() {
        val links = listOf("https://ineedclues.com", "https://pleasehelp.com")
        val quiz = Quiz("id123", "", "", links, emptyList(), "")

        assert(quiz.links).isEqualTo(links)
    }

    @Test
    fun `a quiz should contain a list of questions`() {
        val question1 = Question("", emptyList())
        val question2 = Question("", emptyList())
        val questions = listOf(question1, question2)
        val quiz = Quiz("id123", "", "", emptyList(), questions, "")

        assert(quiz.questions).isEqualTo(questions)
    }

    @Test
    fun `a quiz should grade a response sheet depending on the number of solved questions`() {
        val question1 = mockk<Question>()
        val question2 = mockk<Question>()
        val question3 = mockk<Question>()
        val response1 = Response(emptyList())
        val response2 = Response(emptyList())
        val response3 = Response(emptyList())
        every { question1.isSolvedBy(response1) } returns true
        every { question2.isSolvedBy(response2) } returns false
        every { question3.isSolvedBy(response3) } returns true
        val quizId = "id123"
        val quiz = Quiz(quizId, "", "", emptyList(), listOf(question1, question2, question3), "")
        val responseSheet = ResponseSheet(quizId, listOf(response1, response2, response3))

        assert(quiz.grade(responseSheet)).isEqualTo(2.toDouble().div(3))
    }

    @Test
    fun `a quiz should not attempt to grade a response list of incorrect size`() {
        val question1 = Question("", emptyList())
        val response1 = Response(emptyList())
        val response2 = Response(emptyList())
        val quizId = "id123"
        val quiz = Quiz(quizId, "", "", emptyList(), listOf(question1), "")
        val responseSheet = ResponseSheet(quizId, listOf(response1, response2))

        assertThrows<IllegalArgumentException> { quiz.grade(responseSheet) }
    }

    @Test
    fun `a quiz should provide a success message`() {
        val successMessage = "Well done! Go save the galaxy with this flag: 123456789"
        val quiz = Quiz("id123", "", "", emptyList(), emptyList(), successMessage)

        assert(quiz.successMessage).isEqualTo(successMessage)
    }
}
