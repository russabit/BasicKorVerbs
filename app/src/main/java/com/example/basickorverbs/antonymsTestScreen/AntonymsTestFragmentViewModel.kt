package com.example.basickorverbs.antonymsTestScreen

import androidx.lifecycle.ViewModel
import com.example.basickorverbs.domain.Antonym
import com.example.basickorverbs.domain.Verb
import com.example.basickorverbs.domain.buildAntonymVerbMap
import kotlin.random.Random

class AntonymsTestFragmentViewModel : ViewModel() {

    private val random = Random
    private var listOfPairs: List<Pair<String, String>> = emptyList()

    // number of round played now
    var currentRound = 0
        private set

    // list of rounds played
    private var antonymHistory: ArrayList<Antonym> = ArrayList(5)

    // antonym init
    var antonym = Antonym(
        questionAndAnswerPair = Pair("", ""),
        listOfAnswerOptions = emptyList()
    )
        private set

    fun getBackOneSet(data: List<Verb>) {
        if (currentRound != 0) {
            currentRound--
            antonymHistory[currentRound]
            loadNewRound(data)
        }
    }


    fun loadNewRound(data: List<Verb>) {

        if (listOfPairs.isEmpty()) {
            listOfPairs = buildAntonymVerbMap(data).toList()
        }

        if (currentRound == antonymHistory.size) {
            val entries = listOfPairs

            val pair = entries[random.nextInt(entries.size)]

            antonym.questionAndAnswerPair = pair

            val allOptions = createAnswerOptions(entries)

            antonym.listOfAnswerOptions = allOptions

            antonymHistory.add(Antonym(pair, allOptions))
        } else {
            // load previous round
            val previousRound = currentRound.minus(1)
            antonym.questionAndAnswerPair = antonymHistory[previousRound].questionAndAnswerPair
            antonym.listOfAnswerOptions = antonymHistory[previousRound].listOfAnswerOptions
        }

    }

    private fun createAnswerOptions(entries: List<Pair<String, String>>): MutableList<String> {
        val allOptions = mutableListOf<String>()
        allOptions.add(antonym.questionAndAnswerPair.second)

        while (allOptions.size < 4) {
            val candidate = entries[random.nextInt(entries.size)].second
            if (
                candidate != antonym.questionAndAnswerPair.second &&
                !allOptions.contains(candidate)
            ) {
                allOptions.add(candidate)
            }
        }

        allOptions.shuffle()
        return allOptions
    }

    fun onAnsweringRight(data: List<Verb>) {
        currentRound++
        loadNewRound(data)
    }

    fun isItTheFirstGame(): Boolean = antonymHistory.isEmpty()

    fun getTotalNumberOfRounds() = listOfPairs.size
}