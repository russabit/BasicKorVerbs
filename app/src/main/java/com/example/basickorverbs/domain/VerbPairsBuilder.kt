package com.example.basickorverbs.domain

fun buildAntonymVerbMap(entries: List<Verb>): Set<Pair<String, String>> {
    val entryById = entries.associateBy { it.id }
    val pairs = LinkedHashSet<Pair<String, String>>(200)

    for (entry in entries) {
        for (meaning in entry.meanings) {
            if (meaning.antonymId != 0) {
                val antonymEntry = entryById[meaning.antonymId]
                if (antonymEntry != null) {
                    val thisWord = entry.writing
                    val antonymWord = antonymEntry.writing

                    val firstPair = thisWord to antonymWord
                    val secondPair = antonymWord to thisWord

                    pairs.add(firstPair)
                    pairs.add(secondPair)
                }
            }
        }
    }

    return pairs
}

fun buildSynonymVerbMap(entries: List<Verb>): Set<Pair<String, String>> {
    val entryById = entries.associateBy { it.id }
    val pairs = LinkedHashSet<Pair<String, String>>(200)

    for (entry in entries) {
        for (meaning in entry.meanings) {

            if (meaning.synonymId != 0) {

                val synonymEntry =
                    entryById[meaning.synonymId]

                if (synonymEntry != null) {

                    val thisWord = entry.writing
                    val synonymWord = synonymEntry.writing

                    val normalizedPair =
                        if (thisWord < synonymWord)
                            thisWord to synonymWord
                        else
                            synonymWord to thisWord

                    pairs.add(normalizedPair)
                }
            }
        }
    }

    return pairs
}