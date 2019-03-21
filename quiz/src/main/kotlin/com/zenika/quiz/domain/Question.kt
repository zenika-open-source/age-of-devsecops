package com.zenika.quiz.domain

class Question(
        val label: String,
        val choices: List<Choice>
) {
    fun isSolvedBy(answer: Response): Boolean {
        val expectedChoices = choices
                .filter { it.isCorrect }
                .map { it.label }
                .toSet()
        val actualChoices = answer.choices.toSet()
        return expectedChoices == actualChoices
    }
}
