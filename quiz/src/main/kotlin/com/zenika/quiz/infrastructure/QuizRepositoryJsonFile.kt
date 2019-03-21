package com.zenika.quiz.infrastructure

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zenika.quiz.domain.Quiz
import com.zenika.quiz.domain.QuizRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository

@Repository
class QuizRepositoryJsonFile(@Value("classpath:quizzes/quizzes.json") quizzesFile: Resource) : QuizRepository {


    private val quizzesById: Map<String, Quiz>

    init {
        val file = quizzesFile.inputStream
        val objectMapper = jacksonObjectMapper()
        val quizzes = objectMapper.readValue<List<Quiz>>(file, object : TypeReference<List<Quiz>>() {})
        quizzesById = quizzes.map { it.id to it }.toMap()
    }

    override fun get(id: String): Quiz {
        if (!quizzesById.containsKey(id)) {
            throw IllegalArgumentException("Unknown quiz id")
        }
        return quizzesById.getValue(id)
    }
}
