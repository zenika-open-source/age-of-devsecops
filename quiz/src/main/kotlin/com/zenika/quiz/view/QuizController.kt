package com.zenika.quiz.view


import com.zenika.quiz.application.QuizChecker
import com.zenika.quiz.application.QuizFetcher
import com.zenika.quiz.domain.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping


@Controller
class QuizController(
        private val quizFetcher: QuizFetcher,
        private val quizChecker: QuizChecker
) {

    @GetMapping("/quizzes/{id}")
    fun getQuiz(
            @PathVariable("id") id: String,
            model: Model
    ): String {
        val quiz = quizFetcher.byId(id)
        model.addAttribute("quiz", toView(quiz))
        model.addAttribute("responseSheet", ResponseSheetModel())
        return "quiz"
    }

    @PostMapping("/quizzes/{id}")
    fun submitQuiz(
            @PathVariable("id") id: String,
            @ModelAttribute("responseSheet") responseSheetModel: ResponseSheetModel,
            model: Model
    ): String {
        val quiz = quizFetcher.byId(id)
        val result = quizChecker.check(fromView(quiz, responseSheetModel))
        model.addAttribute("quiz", toView(quiz))
        model.addAttribute("responseSheet", responseSheetModel)
        model.addAttribute("result", result)
        return "quiz"
    }

    private fun fromView(quiz: Quiz, responseSheetModel: ResponseSheetModel): ResponseSheet =
            ResponseSheet(quiz.id, responseSheetModel.responses
                    .mapIndexed { index, responseModel -> fromView(quiz.questions[index], responseModel) })

    private fun fromView(question: Question, responseModel: ResponseModel): Response =
            Response(responseModel.choices.map { choiceModel -> question.choices[choiceModel].label })

    private fun toView(quiz: Quiz): QuizView =
            QuizView(quiz.id, quiz.title, quiz.description, quiz.links, quiz.questions.map { toView(it) })

    private fun toView(question: Question): QuestionView =
            QuestionView(question.label, question.choices.map { it.label })
}
