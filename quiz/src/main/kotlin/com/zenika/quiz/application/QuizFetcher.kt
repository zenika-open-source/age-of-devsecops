package com.zenika.quiz.application

import com.zenika.quiz.domain.Quiz
import com.zenika.quiz.domain.QuizRepository

class QuizFetcher(private val quizRepository: QuizRepository) {
    fun byId(quizId: String): Quiz {
        return quizRepository.get(quizId)
    }
}
