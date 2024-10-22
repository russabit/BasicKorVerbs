package com.example.basickorverbs.domain

data class Meaning(
    val engTranslation: String,
    val rusTranslation: String,
    val examples: List<Example>,
    val antonymId: Number,
    val synonymId: Number
)
