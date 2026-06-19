package com.example.basickorverbs.antonymsTestScreen

import androidx.lifecycle.ViewModel
import com.example.basickorverbs.domain.Round
import com.example.basickorverbs.domain.Verb
import com.example.basickorverbs.domain.buildAntonymVerbMap
import com.example.basickorverbs.domain.buildSynonymVerbMap
import kotlin.random.Random

class AntonymsTestFragmentViewModel : ViewModel() {

    private val random = Random
    private lateinit var listOfPairs: MutableList<Pair<Question, Answer>>
    private lateinit var synonymMap : Map<String, Set<String>>

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
        synonymMap = buildSynonymMap(buildSynonymVerbMap(data))
    }

    // handles both cases because we can answer right on the round we already played
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

        val newAnswerOptions =
            createAnswerOptions(random, listOfPairs, newPair, synonymMap)

        round = Round(newPair, newAnswerOptions)

        roundHistory.add(Round(newPair, newAnswerOptions))

        listOfPairs.remove(newPair) // to get rid of possible doubles
    }

    companion object {

        // only normalized pair
        fun createAnswerOptions(
            random: Random,
            entries: List<Pair<Question, Answer>>,
            pair: Pair<Question, Answer>,
            synonymMap: Map<String, Set<String>>
        ): MutableList<String> {

            val allOptions = mutableListOf<String>()

            val question = pair.first
            val answer = pair.second
            allOptions.add(answer)

            while (allOptions.size < 4) {

                val candidate = entries[random.nextInt(entries.size)].toList().random()

                if (
                    candidate != answer &&
                    candidate !in allOptions &&
                    candidate !in synonymMap[question].orEmpty()
                ) {
                    allOptions.add(candidate)
                }
            }

            allOptions.shuffle()
            return allOptions
        }

        fun buildSynonymMap(listOfSynonymPairs: Set<Pair<Question, Answer>>): Map<String, Set<String>> {
            return buildMap {

                listOfSynonymPairs.forEach { (a, b) ->

                    put(
                        a,
                        getOrDefault(a, emptySet()) + b
                    )

                    put(
                        b,
                        getOrDefault(b, emptySet()) + a
                    )
                }
            }
        }
    }

}

typealias Question = String
typealias Answer = String