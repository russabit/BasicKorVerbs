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
import com.example.basickorverbs.firstScreen.FirstScreenAdapter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var adapter: SecondScreenAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.recyclerViewSecondFragment.layoutManager = LinearLayoutManager(requireContext())

        val position = arguments?.getInt("verbPosition")

        val viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainActivityViewModel::class.java)

        viewModel.dataList
            .observe(viewLifecycleOwner) { list ->
                list?.let {
                    if (position != null) {
                        viewModel.dataList.value?.get(position).let { verb ->
                            if (verb != null) {
                                adapter = SecondScreenAdapter(
                                    verb.id,
                                    verb.meanings
                                )
                                (activity as? AppCompatActivity)?.supportActionBar?.title = verb.writing
                            }
                            binding.recyclerViewSecondFragment.adapter = adapter

                        }
                    }
                }
            }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}