package com.example.basickorverbs.firstScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basickorverbs.data.testModelData
import com.example.basickorverbs.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        // Задаём менеджер компоновки (например, LinearLayoutManager для вертикального списка)
        binding.recyclerViewFirstFragment.layoutManager = LinearLayoutManager(requireContext())

        // Инициализируем адаптер и присваиваем его RecyclerView
        val adapter = FirstScreenAdapter(
            testModelData
        )
        binding.recyclerViewFirstFragment.adapter = adapter

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