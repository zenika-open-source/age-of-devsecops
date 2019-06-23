package com.zenika.quiz.application

import com.zenika.quiz.domain.QuizRepository
import com.zenika.quiz.domain.ResponseSheet

class QuizChecker(private val quizRepository: QuizRepository) {
    fun check(responseSheet: ResponseSheet): String {
        val quiz = quizRepository.get(responseSheet.quizId)
        val grade = quiz.grade(responseSheet)
        return if (grade < 1.0) {
            "C'est pas bon ! Essaie encore..."
        } else {
            quiz.successMessage
        }
    }
}
