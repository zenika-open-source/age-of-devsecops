package com.zenika.quiz.domain

interface QuizRepository {
    fun get(id: String): Quiz
}
