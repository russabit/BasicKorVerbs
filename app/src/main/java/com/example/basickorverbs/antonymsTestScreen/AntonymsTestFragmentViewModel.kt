package com.example.basickorverbs.antonymsTestScreen

import androidx.lifecycle.ViewModel
import com.example.basickorverbs.domain.Round
import com.example.basickorverbs.domain.Verb
import com.example.basickorverbs.domain.buildAntonymVerbMap
import kotlin.random.Random

class AntonymsTestFragmentViewModel : ViewModel() {

    private val random = Random
    private lateinit var listOfPairs: MutableList<Pair<String, String>>

    // number of round played now
    var currentRound = 0
        private set

    // list of rounds played
    private var roundHistory: ArrayList<Round> = ArrayList(5)

    // antonym init
    lateinit var round : Round
        private set

    fun getBackOneSet() {
        if (currentRound != 0) {
            currentRound--
            loadRound()
        }
    }

    fun initList(data: List<Verb>) {
        if (!this::listOfPairs.isInitialized) {
            listOfPairs = buildAntonymVerbMap(data).toMutableList()
        }
    }

    // first initList()
    fun loadRound() {

        val isThisRoundNew = currentRound == roundHistory.size

        if (isThisRoundNew) loadNewRound() else loadPreviousRound()

    }

    fun onAnsweringRight() {
        currentRound++
        loadRound()
    }

    fun isItTheFirstGame(): Boolean = roundHistory.isEmpty()

    fun getTotalNumberOfRounds() = listOfPairs.size + roundHistory.size

    private fun loadPreviousRound() {
        round.questionAndAnswerPair = roundHistory[currentRound].questionAndAnswerPair
        round.listOfAnswerOptions = roundHistory[currentRound].listOfAnswerOptions
    }

    private fun loadNewRound() {

        val newPair = listOfPairs[random.nextInt(listOfPairs.size)]
        val newAnswerOptions = createAnswerOptions(listOfPairs, newPair)

        round = Round(newPair, newAnswerOptions)

        roundHistory.add(Round(newPair, newAnswerOptions))

        listOfPairs.remove(newPair) // to get rid of possible doubles
    }

    private fun createAnswerOptions(entries: List<Pair<String, String>>, pair: Pair<String, String>): MutableList<String> {
        val allOptions = mutableListOf<String>()
        allOptions.add(pair.second)

        while (allOptions.size < 4) {
            val candidate = entries[random.nextInt(entries.size)].second
            if (
                candidate != pair.second &&
                !allOptions.contains(candidate)
                // todo exclude synonym
            ) {
                allOptions.add(candidate)
            }
        }

        allOptions.shuffle()
        return allOptions
    }
}