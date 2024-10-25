package com.example.basickorverbs.secondScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basickorverbs.data.testModelData
import com.example.basickorverbs.databinding.FragmentSecondBinding
import com.example.basickorverbs.databinding.FragmentThirdBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        binding.recyclerViewThirdFragment.layoutManager = LinearLayoutManager(requireContext())

        val verbPosition = arguments?.getInt("verbId")
        val meaningPosition = arguments?.getInt("meaningPosition")


            (if (meaningPosition != null && verbPosition != null) {
                testModelData.find { it.id == verbPosition }
                    ?.meanings?.get(meaningPosition)
                    ?.examples
            } else {
                throw NullPointerException("verbPosition = " + verbPosition.toString() + "\n"
                        + "meaningPosition = " + meaningPosition.toString())
            })
                .let {
                    if (it != null) {
                        binding.recyclerViewThirdFragment.adapter = ThirdScreenAdapter(it)
                    }
                }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}