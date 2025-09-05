package com.example.basickorverbs.antonymsTestScreen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.basickorverbs.R
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
        buttons = listOf(
            view.findViewById(R.id.btnOption1),
            view.findViewById(R.id.btnOption2),
            view.findViewById(R.id.btnOption3),
            view.findViewById(R.id.btnOption4)
        )

        loadNewRound()

        return view
    }

    private fun loadNewRound() {
        // Выбираем случайный глагол
        val entries = antonyms.entries.toList()
        val pair = entries[random.nextInt(entries.size)]
        currentWord = pair.key
        val correctAnswer = pair.value

        tvVerb.text = currentWord

        // Собираем варианты
        val allOptions = mutableListOf<String>()
        allOptions.add(correctAnswer)

        // Добавляем 3 случайных неправильных
        while (allOptions.size < 4) {
            val candidate = entries[random.nextInt(entries.size)].value
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
                    loadNewRound()
                } else {
                    // Неправильно → подсветка
                    button.setBackgroundColor(Color.RED)
                }
            }
            button.setBackgroundColor(Color.LTGRAY)
        }
    }


}