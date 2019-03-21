package com.zenika.quiz

import com.zenika.quiz.application.QuizChecker
import com.zenika.quiz.application.QuizFetcher
import com.zenika.quiz.domain.QuizRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Autowired
    lateinit var quizRepository: QuizRepository

    @Bean
    fun quizzFetcher() = QuizFetcher(quizRepository)

    @Bean
    fun quizzChecker() = QuizChecker(quizRepository)
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
