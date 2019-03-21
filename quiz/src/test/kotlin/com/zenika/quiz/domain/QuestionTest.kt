package com.zenika.quiz.domain

import assertk.assert
import assertk.assertions.isEqualTo
import kotlin.test.Test

class QuestionTest {

    @Test
    fun `a question should have a label`() {
        val label = "Question label"
        val question = Question(label, emptyList())

        assert(question.label).isEqualTo(label)
    }

    @Test
    fun `a question should contain a list of choices`() {
        val choice1 = Choice("", true)
        val choice2 = Choice("", true)
        val choices = listOf(choice1, choice2)
        val question = Question("", choices)

        assert(question.choices).isEqualTo(choices)
    }

    @Test
    fun `a question should be solved by a response if all correct choices are provided`() {
        val question = Question("Java is...", listOf(
                Choice("A programming language", true),
                Choice("The name of an island", true),
                Choice("The same thing as JavaScript", false)
        ))
        val answer = Response(listOf(
                "A programming language",
                "The name of an island"
        ))
        assert(question.isSolvedBy(answer)).isEqualTo(true)
    }

    @Test
    fun `a question should not be solved by a response if a correct choice is missing`() {
        val question = Question("Java is...", listOf(
                Choice("A programming language", true),
                Choice("The name of an island", true),
                Choice("The same thing as JavaScript", false)
        ))
        val answer = Response(listOf(
                "A programming language"
        ))
        assert(question.isSolvedBy(answer)).isEqualTo(false)
    }

    @Test
    fun `a question should not be solved by a response if an incorrect choice is provided`() {
        val question = Question("Java is...", listOf(
                Choice("A programming language", true),
                Choice("The name of an island", true),
                Choice("The same thing as JavaScript", false)
        ))
        val answer = Response(listOf(
                "A programming language",
                "The name of an island",
                "The same thing as JavaScript"
        ))
        assert(question.isSolvedBy(answer)).isEqualTo(false)
    }

    @Test
    fun `a question should not be solved by a response if an unknown choice is provided`() {
        val question = Question("Java is...", listOf(
                Choice("A programming language", true),
                Choice("The name of an island", true),
                Choice("The same thing as JavaScript", false)
        ))
        val answer = Response(listOf(
                "A programming language",
                "The name of an island",
                "Some other response"
        ))
        assert(question.isSolvedBy(answer)).isEqualTo(false)
    }
}
