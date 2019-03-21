package com.zenika.quiz.view

data class QuizView(
        val id: String,
        val title: String,
        val description: String,
        val links: List<String>,
        val questions: List<QuestionView>
)
