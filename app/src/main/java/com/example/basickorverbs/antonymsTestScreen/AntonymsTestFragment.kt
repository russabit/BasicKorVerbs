package com.example.basickorverbs.antonymsTestScreen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.basickorverbs.MainActivityViewModel
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Verb
import kotlin.random.Random

class AntonymsTestFragment : Fragment() {

    private lateinit var tvVerb: TextView
    private lateinit var buttons: List<Button>

    private val random = Random

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_antonym_training, container, false)

        tvVerb = view.findViewById(R.id.tvVerb)

        buttons = listOf(
            view.findViewById(R.id.btnOption1),
            view.findViewById(R.id.btnOption2),
            view.findViewById(R.id.btnOption3),
            view.findViewById(R.id.btnOption4)
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[MainActivityViewModel::class.java]

        viewModel.dataList
            .observe(viewLifecycleOwner) { data ->

                if (viewModel.antonymTestCurrentWord.isEmpty()) {
                    loadNewRound(data)
                } else {
                    restoreRound()
                }

                //finds the right verb and let's us go to the second frag with list of meanings
                val verb = data.find { it.writing == viewModel.antonymTestCurrentWord }
                val bundle = Bundle()
                bundle.putInt("verbPosition", data.indexOf(verb))

                tvVerb.setOnDoubleClickListener {
                    findNavController().navigate(R.id.action_test_antonyms_to_SecondFragment, bundle)
                }
            }

        return view
    }

    private fun loadNewRound(data: List<Verb>) {

        val entries = buildAntonymVerbMap(data).toList()
        val pair = entries[random.nextInt(entries.size)]

        viewModel.antonymTestCurrentWord = pair.first
        viewModel.antonymTestCorrectAnswer = pair.second

        val allOptions = mutableListOf<String>()
        allOptions.add(viewModel.antonymTestCorrectAnswer)

        while (allOptions.size < 4) {
            val candidate = entries[random.nextInt(entries.size)].second
            if (
                candidate != viewModel.antonymTestCorrectAnswer &&
                !allOptions.contains(candidate)
            ) {
                allOptions.add(candidate)
            }
        }

        allOptions.shuffle()
        viewModel.antonymTestCurrentOptions = allOptions

        restoreRound()
    }

    private fun restoreRound() {
        tvVerb.text = viewModel.antonymTestCurrentWord

        buttons.forEachIndexed { index, button ->
            button.text = viewModel.antonymTestCurrentOptions[index]

            button.setOnClickListener {
                if (button.text == viewModel.antonymTestCorrectAnswer) {
                    loadNewRound(viewModel.dataList.value ?: emptyList())
                } else {
                    button.setBackgroundColor(Color.RED)
                }
            }

            button.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun buildAntonymVerbMap(entries: List<Verb>): Set<Pair<String, String>> {
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

    @SuppressLint("ClickableViewAccessibility")
    fun TextView.setOnDoubleClickListener(onDoubleClick: () -> Unit) {
        val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                onDoubleClick()
                return true
            }
        })

        setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

}