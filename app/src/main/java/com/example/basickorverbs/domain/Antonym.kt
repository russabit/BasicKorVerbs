package com.example.basickorverbs.domain

data class Antonym(
    var questionAndAnswerPair: Pair<String, String>,
    var listOfAnswerOptions: List<String>
)
