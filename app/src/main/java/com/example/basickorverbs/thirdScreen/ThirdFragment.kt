package com.example.basickorverbs.secondScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basickorverbs.MainActivityViewModel
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
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
                .get(MainActivityViewModel::class.java).dataList.value?.find {
                it.id == verbPosition }
                ?.meanings?.get(meaningPosition)
                ?.examples
        } else {
            throw NullPointerException(
                "verbPosition = " + verbPosition.toString() + "\n"
                        + "meaningPosition = " + meaningPosition.toString()
            )
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}