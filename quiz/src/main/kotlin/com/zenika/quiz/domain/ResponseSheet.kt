package com.zenika.quiz.domain

data class ResponseSheet(
        val quizId: String,
        val responses: List<Response>
)
