package com.example.basickorverbs.thirdScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basickorverbs.MainActivityViewModel
import com.example.basickorverbs.databinding.FragmentThirdBinding
import com.example.basickorverbs.secondScreen.ThirdScreenAdapter

/**
 * A simple [Fragment] to represent a list of examples after tapping on concrete meaning of a verb.
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

        initRecyclerView()

        return binding.root

    }

    private fun initRecyclerView() {
        val verbPosition = arguments?.getInt("verbId")
        val meaningPosition = arguments?.getInt("meaningPosition")

        (if (meaningPosition != null && verbPosition != null) {
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
                .get(MainActivityViewModel::class.java)
                .dataList
                .value
                ?.find {
                    it.id == verbPosition
                }
                ?.meanings
                ?.get(meaningPosition)
                .also {
                    if (it != null) {
                        setScreenTitle(it.engTranslation)
                    }
                }
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
    }

    private fun setScreenTitle(verbMeaning: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = verbMeaning
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}