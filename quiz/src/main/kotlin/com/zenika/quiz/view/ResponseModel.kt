package com.zenika.quiz.view

data class ResponseModel(
        var choices: MutableList<Int> = emptyList<Int>().toMutableList()
)
