package com.example.basickorverbs.AboutScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.basickorverbs.MainActivityViewModel
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Verb

class AboutFragment : Fragment() {

    private lateinit var tvVerbCount: TextView
    private lateinit var tvMeaningCount: TextView
    private lateinit var tvExampleCount: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        tvVerbCount = view.findViewById(R.id.tvVerbCount)
        tvMeaningCount = view.findViewById(R.id.tvMeaningCount)
        tvExampleCount = view.findViewById(R.id.tvExampleCount)

        val viewmodel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainActivityViewModel::class.java)

        viewmodel.dataList
            .observe(viewLifecycleOwner) { data ->
                showStats(data)
            }

        return view
    }

    private fun showStats(verbs: List<Verb>) {
        val verbCount = verbs.size
        val meaningCount = verbs.sumOf { it.meanings.size }
        val exampleCount = verbs.sumOf { verb -> verb.meanings.sumOf { it.examples.size } }

        tvVerbCount.text = "Глаголов: $verbCount"
        tvMeaningCount.text = "Значений: $meaningCount"
        tvExampleCount.text = "Примеров: $exampleCount"
    }
}
