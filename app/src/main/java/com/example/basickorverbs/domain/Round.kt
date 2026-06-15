package com.example.basickorverbs.domain

data class Round(
    var questionAndAnswerPair: Pair<String, String>,
    var listOfAnswerOptions: List<String>
)
