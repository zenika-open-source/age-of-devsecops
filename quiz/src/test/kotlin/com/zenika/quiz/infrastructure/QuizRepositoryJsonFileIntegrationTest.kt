package com.zenika.quiz.infrastructure

import assertk.assert
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.zenika.quiz.domain.Choice
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.Test


@RunWith(SpringRunner::class)
@SpringBootTest
class QuizRepositoryJsonFileIntegrationTest {

    @Autowired
    lateinit var quizRepositoryJsonFile: QuizRepositoryJsonFile

    @Test
    fun `repository should get quiz by id in JSON file`() {
        val quiz = quizRepositoryJsonFile.get("123")

        assert(quiz.id).isEqualTo("123")
        assert(quiz.title).isEqualTo("MyQuiz")
        assert(quiz.description).isEqualTo("Welcome to my quiz")
        assert(quiz.links).isEqualTo(listOf("https://labs.zenika.com", "https://romain.vernoux.fr"))
        assert(quiz.questions).hasSize(2)
        assert(quiz.questions[0].label).isEqualTo("What is Java?")
        assert(quiz.questions[0].choices).isEqualTo(listOf(
                Choice("A programming language", true),
                Choice("An island", true),
                Choice("The same thing as JavaScript", false)
        ))
        assert(quiz.questions[1].label).isEqualTo("What is JavaScript?")
        assert(quiz.questions[1].choices).isEqualTo(listOf(
                Choice("A programming language", true),
                Choice("An island", false),
                Choice("The same thing as Java", false)
        ))
        assert(quiz.successMessage).isEqualTo("Well done champion! Here is your code: 123456789")
    }
}
