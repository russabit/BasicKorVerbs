package com.example.basickorverbs.domain

data class Verb(
    val id: Int,
    val writing: String,
    val actualPronunciation: String,
    val infinitive: String,
    val meanings: List<Meaning>
    )
