package com.example.basickorverbs.secondScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basickorverbs.MainActivityViewModel
import com.example.basickorverbs.databinding.FragmentSecondBinding
import com.example.basickorverbs.domain.Verb

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var verbName : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.recyclerViewSecondFragment.layoutManager = LinearLayoutManager(requireContext())

        val position = arguments?.getInt("verbPosition")

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val adapter = position
            ?.let { viewModel.verbsList[it] }
            ?.let { verb: Verb ->
                verbName = verb.writing
                SecondScreenAdapter(
                    verb.id,
                    verb.meanings
                )
            }

        binding.recyclerViewSecondFragment.adapter = adapter

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = verbName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}