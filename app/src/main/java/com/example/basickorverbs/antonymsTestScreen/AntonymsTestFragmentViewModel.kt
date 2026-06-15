package com.example.basickorverbs.antonymsTestScreen

import androidx.lifecycle.ViewModel
import com.example.basickorverbs.domain.Antonym
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
    private var antonymHistory: ArrayList<Antonym> = ArrayList(5)

    // antonym init
    lateinit var antonym : Antonym
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

        val isThisRoundNew = currentRound == antonymHistory.size

        if (isThisRoundNew) loadNewRound() else loadPreviousRound()

    }

    fun onAnsweringRight() {
        currentRound++
        loadRound()
    }

    fun isItTheFirstGame(): Boolean = antonymHistory.isEmpty()

    fun getTotalNumberOfRounds() = listOfPairs.size + antonymHistory.size

    private fun loadPreviousRound() {
        antonym.questionAndAnswerPair = antonymHistory[currentRound].questionAndAnswerPair
        antonym.listOfAnswerOptions = antonymHistory[currentRound].listOfAnswerOptions
    }

    private fun loadNewRound() {
        val entries = listOfPairs

        val pair = entries[random.nextInt(entries.size)]
        val allOptions = createAnswerOptions(entries, pair)

        antonym = Antonym(pair, allOptions)

        antonymHistory.add(Antonym(pair, allOptions))

        listOfPairs.remove(pair) // to get rid of possible doubles
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