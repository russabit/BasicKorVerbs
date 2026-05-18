package com.example.basickorverbs.domain

fun buildAntonymVerbMap(entries: List<Verb>): Set<Pair<String, String>> {
    val entryById = entries.associateBy { it.id }
    val pairs = mutableSetOf<Pair<String, String>>()

    for (entry in entries) {
        for (meaning in entry.meanings) {
            if (meaning.antonymId != 0) {
                val antonymEntry = entryById[meaning.antonymId.toInt()]
                if (antonymEntry != null) {
                    val thisWord = entry.writing
                    val antonymWord = antonymEntry.writing

                    val normalizedPair = if (thisWord < antonymWord) {
                        thisWord to antonymWord
                    } else {
                        antonymWord to thisWord
                    }

                    pairs.add(normalizedPair)
                }
            }
        }
    }

    return pairs
}