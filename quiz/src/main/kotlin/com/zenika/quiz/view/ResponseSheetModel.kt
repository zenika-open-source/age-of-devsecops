package com.zenika.quiz.view

data class ResponseSheetModel(
        var responses: MutableList<ResponseModel> = emptyList<ResponseModel>().toMutableList()
)
