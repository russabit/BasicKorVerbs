package com.example.basickorverbs.firstScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basickorverbs.MainActivityViewModel
import com.example.basickorverbs.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var adapter: FirstScreenAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.recyclerViewFirstFragment.layoutManager = LinearLayoutManager(requireContext())

        val viewmodel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainActivityViewModel::class.java)

        viewmodel.dataList
            .observe(viewLifecycleOwner) { data ->
                adapter = FirstScreenAdapter(data)
                binding.recyclerViewFirstFragment.adapter = adapter
            }

        binding.recyclerViewFirstFragment.adapter = this.context?.let {
            viewmodel.getVerbListFromString(it)
                ?.let { FirstScreenAdapter(it) }
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}