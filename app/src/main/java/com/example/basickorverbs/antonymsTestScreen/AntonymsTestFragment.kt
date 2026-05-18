package com.example.basickorverbs.antonymsTestScreen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.basickorverbs.MainActivityViewModel
import com.example.basickorverbs.R
import com.example.basickorverbs.domain.Antonym
import com.example.basickorverbs.domain.Verb
import com.example.basickorverbs.domain.buildAntonymVerbMap
import kotlin.random.Random

class AntonymsTestFragment : Fragment() {

    private lateinit var tvVerb: TextView
    private lateinit var buttons: List<Button>

    private val random = Random

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_antonym_training, container, false)

        tvVerb = view.findViewById(R.id.tvVerb)

        initButtons(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[MainActivityViewModel::class.java]

        viewModel.dataList
            .observe(viewLifecycleOwner) { data ->

                if (viewModel.antonymHistory.isNullOrEmpty()) {
                    loadNewRound(data)
                } else {
                    restoreRound()
                }
            }

        return view
    }

    private fun initButtons(view: View) {
        buttons = listOf(
            view.findViewById(R.id.btnOption1),
            view.findViewById(R.id.btnOption2),
            view.findViewById(R.id.btnOption3),
            view.findViewById(R.id.btnOption4)
        )
    }

    private fun setDoubleClickNavigation(data: List<Verb>) {

        //finds the right verb and let's us go to the second fragment with list of meanings
        val verb = data.find { it.writing == viewModel.antonym.questionAndAnswerPair.first }
        val bundle = Bundle()
        bundle.putInt("verbPosition", data.indexOf(verb))

        tvVerb.setOnDoubleClickListener {
            findNavController().navigate(R.id.action_test_antonyms_to_SecondFragment, bundle)
        }
    }

    private fun setOnLongPressOptionWordNavigation(data: List<Verb>) {

        buttons.forEach { button: Button ->
            button.setOnLongTapAndOnClickListeners(
                onLongPress = {
                    val verb = data.find {
                        it.writing == button.text
                    }
                    val bundle = Bundle()
                    bundle.putInt("verbPosition", data.indexOf(verb))

                    findNavController().navigate(
                        R.id.action_test_antonyms_to_SecondFragment,
                        bundle
                    )
                }, onClick = {
                    if (button.text == viewModel.antonym.questionAndAnswerPair.second) {
                        viewModel.currentRound++
                        loadNewRound(viewModel.dataList.value ?: emptyList())
                    } else {
                        button.setBackgroundColor(Color.RED)
                    }
                }
            )
        }
    }

    private fun loadNewRound(data: List<Verb>) {

        val entries = buildAntonymVerbMap(data).toList()
        val pair = entries[random.nextInt(entries.size)]

        viewModel.antonym.questionAndAnswerPair = pair

        val allOptions = createAnswerOptions(entries)

        viewModel.antonym.listOfAnswerOptions = allOptions

        // init the list (can be better)
        if (viewModel.antonymHistory.isNullOrEmpty()) { viewModel.antonymHistory = ArrayList(5)
        }

        viewModel.antonymHistory?.add(Antonym(pair, allOptions))

        setOnToolbarBackArrowPressedNavigation()

        restoreRound()
    }

    private fun createAnswerOptions(entries: List<Pair<String, String>>): MutableList<String> {
        val allOptions = mutableListOf<String>()
        allOptions.add(viewModel.antonym.questionAndAnswerPair.second)

        while (allOptions.size < 4) {
            val candidate = entries[random.nextInt(entries.size)].second
            if (
                candidate != viewModel.antonym.questionAndAnswerPair.second &&
                !allOptions.contains(candidate)
            ) {
                allOptions.add(candidate)
            }
        }

        allOptions.shuffle()
        return allOptions
    }

    private fun setOnToolbarBackArrowPressedNavigation() {
        (activity as? AppCompatActivity)?.onBackPressedDispatcher?.addCallback {
            goBackOneRound()
        }
    }

    private fun goBackOneRound() {
        viewModel.getBackOneSet()
    }

    private fun restoreRound() {
        setCurrentWordTextView()
        setButtonsTextsAndOnLongPressNavigation()
    }

    private fun setButtonsTextsAndOnLongPressNavigation() {
        buttons.forEachIndexed { index, button ->
            button.text = viewModel.antonym.listOfAnswerOptions[index]

            setDoubleClickNavigation(viewModel.dataList.value ?: emptyList())
            setOnLongPressOptionWordNavigation(viewModel.dataList.value ?: emptyList())

            button.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun setCurrentWordTextView() {
        tvVerb.text = viewModel.antonym.questionAndAnswerPair.first
    }

    @SuppressLint("ClickableViewAccessibility")
    fun TextView.setOnDoubleClickListener(onDoubleClick: () -> Unit) {
        val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    onDoubleClick()
                    return true
                }
            })

        setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun Button.setOnLongTapAndOnClickListeners(onLongPress: () -> Unit, onClick: () -> Unit) {
        val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onLongPress(e: MotionEvent) {
                    onLongPress()
                    super.onLongPress(e)
                }

                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    onClick()
                    return super.onSingleTapConfirmed(e)
                }
            })

        setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

}