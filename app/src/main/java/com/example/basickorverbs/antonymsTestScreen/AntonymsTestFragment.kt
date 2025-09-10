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
import com.example.basickorverbs.MainActivityViewModel
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Verb
import kotlin.random.Random

class AntonymsTestFragment : Fragment() {

    private lateinit var tvVerb: TextView
    private lateinit var buttons: List<Button>

    // Пример словаря антонимов
    private val antonyms = mapOf(
        "크다" to "작다",
        "열다" to "닫다",
        "빠르다" to "느리다",
        "시작하다" to "끝내다",
        "올라가다" to "내려가다"
    )

    private val random = Random
    private var currentWord: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_antonym_training, container, false)

        tvVerb = view.findViewById(R.id.tvVerb)
/*        tvVerb.setOnDoubleClickListener {
            val action =
                .actionTrainingFragmentToVerbDetailsFragment(currentVerbId)
            findNavController().navigate(action)
        }*/
        buttons = listOf(
            view.findViewById(R.id.btnOption1),
            view.findViewById(R.id.btnOption2),
            view.findViewById(R.id.btnOption3),
            view.findViewById(R.id.btnOption4)
        )

        val viewmodel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainActivityViewModel::class.java)

        viewmodel.dataList
            .observe(viewLifecycleOwner) { data ->
                loadNewRound(data)
            }



        return view
    }

    private fun loadNewRound(data: List<Verb>) {

        // Выбираем случайный глагол
        val entries = buildAntonymVerbMap(data).toList()
        val pair = entries[random.nextInt(entries.size)]
        currentWord = pair.first
        val correctAnswer = pair.second

        tvVerb.text = currentWord

        // Собираем варианты
        val allOptions = mutableListOf<String>()
        allOptions.add(correctAnswer)

        // Добавляем 3 случайных неправильных
        while (allOptions.size < 4) {
            val candidate = entries[random.nextInt(entries.size)].second
            if (candidate != correctAnswer && !allOptions.contains(candidate)) {
                allOptions.add(candidate)
            }
        }

        // Перемешиваем кнопки
        allOptions.shuffle()

        // Устанавливаем тексты и обработчики клика
        buttons.forEachIndexed { index, button ->
            button.text = allOptions[index]
            button.setOnClickListener {
                if (button.text == correctAnswer) {
                    // Правильно → загружаем следующий
                    loadNewRound(data)
                } else {
                    // Неправильно → подсветка
                    button.setBackgroundColor(Color.RED)
                }
            }
            button.setBackgroundColor(Color.LTGRAY)
        }
    }

    fun buildAntonymVerbMap(entries: List<Verb>): Set<Pair<String, String>> {
        val entryById = entries.associateBy { it.id }
        val pairs = mutableSetOf<Pair<String, String>>()

        for (entry in entries) {
            for (meaning in entry.meanings) {
                if (meaning.antonymId != 0) {
                    val antonymEntry = entryById.get(meaning.antonymId.toInt())
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