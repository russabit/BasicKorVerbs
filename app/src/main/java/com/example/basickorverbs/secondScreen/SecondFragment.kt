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

/**
 * A simple [Fragment] to show a list of meanings for a chosen korean verb.
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

        if (position != null) {
            initRecyclerView(viewModel, position)
        }

        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initRecyclerView(viewModel: MainActivityViewModel, position: Int) {
        viewModel.dataList
            .observe(viewLifecycleOwner) { list ->
                list?.let {
                    viewModel.dataList.value?.get(position).let { verb ->
                        if (verb != null) {
                            adapter = SecondScreenAdapter(
                                verb.id,
                                verb.meanings
                            )
                            setScreenTitle(verb.writing)
                        }
                        binding.recyclerViewSecondFragment.adapter = adapter
                    }
                }
            }
    }

    private fun setScreenTitle(verbWriting: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = verbWriting
    }
}