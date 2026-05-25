package com.example.basickorverbs.antonymsTestScreen

import androidx.lifecycle.ViewModel
import com.example.basickorverbs.domain.Antonym
import com.example.basickorverbs.domain.Verb
import com.example.basickorverbs.domain.buildAntonymVerbMap
import kotlin.random.Random

class AntonymsTestFragmentViewModel : ViewModel() {

    private val random = Random

    // number of round played now
    private var currentRound = 1

    // list of rounds played
    private var antonymHistory: ArrayList<Antonym>? = null

    // antonym init
    var antonym = Antonym(
        questionAndAnswerPair = Pair("", ""),
        listOfAnswerOptions = emptyList()
    )

    fun getBackOneSet() {
        antonymHistory?.get(currentRound.minus(1))
        // trigger change in livedata ?
        //_dataList.value = dataList.value
    }


    fun loadNewRound(data: List<Verb>) {

        val entries = buildAntonymVerbMap(data).toList()
        val pair = entries[random.nextInt(entries.size)]

        antonym.questionAndAnswerPair = pair

        val allOptions = createAnswerOptions(entries)

        antonym.listOfAnswerOptions = allOptions

        // init the list (can be better)
        if (antonymHistory.isNullOrEmpty()) {
            antonymHistory = ArrayList(5)
        }

        antonymHistory?.add(Antonym(pair, allOptions))
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

    fun isItTheFirstGame(): Boolean = antonymHistory.isNullOrEmpty()
}