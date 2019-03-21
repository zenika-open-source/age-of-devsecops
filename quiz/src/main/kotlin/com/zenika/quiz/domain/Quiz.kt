package com.zenika.quiz.domain

class Quiz(
        val id: String,
        val title: String,
        val description: String,
        val links: List<String>,
        val questions: List<Question>,
        val successMessage: String
) {
    fun grade(responseSheet: ResponseSheet): Double {
        if (responseSheet.responses.size != questions.size) throw IllegalArgumentException("Wrong number of responses")
        val correctResponsesCount = questions
                .filterIndexed { index, question -> question.isSolvedBy(responseSheet.responses[index]) }
                .size
        return correctResponsesCount.toDouble().div(questions.size)
    }
}
