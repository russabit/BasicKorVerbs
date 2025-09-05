package com.example.basickorverbs.examplesTrainingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.basickorverbs.MainActivityViewModel
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Example
import com.example.basickorverbs.domain.Meaning
import com.example.basickorverbs.domain.Verb

class ExampleTrainingFragment : Fragment() {

    private lateinit var tvExampleKorean: TextView
    private lateinit var tvAnswer: TextView
    private lateinit var btnShowRus: Button
    private lateinit var btnShowEng: Button
    private lateinit var btnShowMeaning: Button
    private lateinit var btnShowAntonym: Button
    private lateinit var btnNext: Button

    private var currentExample: Example? = null
    private var currentMeaning: Meaning? = null
    private var currentVerb: Verb? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_example_training, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvExampleKorean = view.findViewById(R.id.tvExampleKorean)
        tvAnswer = view.findViewById(R.id.tvAnswer)
        btnShowRus = view.findViewById(R.id.btnShowRus)
        btnShowEng = view.findViewById(R.id.btnShowEng)
        btnShowMeaning = view.findViewById(R.id.btnShowMeaning)
        btnShowAntonym = view.findViewById(R.id.btnShowAntonym)
        btnNext = view.findViewById(R.id.btnNext)

        val viewmodel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainActivityViewModel::class.java)

        viewmodel.dataList
            .observe(viewLifecycleOwner) { data ->

                btnShowAntonym.setOnClickListener {
                    val antonymId = currentMeaning?.antonymId ?: 0
                    if (antonymId != 0) {
                        val antonymVerb = data.find { it.id == antonymId }
                        tvAnswer.text = "Антоним: ${antonymVerb?.writing ?: "нет"}"
                    } else {
                        tvAnswer.text = "Антоним отсутствует"
                    }
                }


                btnShowRus.setOnClickListener { tvAnswer.text = currentExample?.rusTranslation ?: "Нет перевода" }
                btnShowEng.setOnClickListener { tvAnswer.text = currentExample?.engTranslation ?: "No translation" }
                btnShowMeaning.setOnClickListener {
                    tvAnswer.text = "${currentMeaning?.rusTranslation ?: ""}\n${currentMeaning?.engTranslation ?: ""}"
                }


                btnNext.setOnClickListener { loadNewExample(data) }

                loadNewExample(data)

            }
    }

    private fun loadNewExample(verbs: List<Verb>) {
        tvAnswer.text = ""

        // случайный глагол
        currentVerb = verbs.random()
        // случайное значение
        currentMeaning = currentVerb!!.meanings.random()
        // случайный пример
        currentExample = currentMeaning!!.examples.random()

        tvExampleKorean.text = currentExample!!.korean
    }
}
