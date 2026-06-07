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
import com.example.basickorverbs.domain.Verb

class AntonymsTestFragment : Fragment() {

    private lateinit var tvVerb: TextView
    private lateinit var tvRound: TextView
    private lateinit var buttons: List<Button>


    private lateinit var activityViewModel: MainActivityViewModel
    private lateinit var fragmentViewModel: AntonymsTestFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_antonym_training, container, false)

        tvVerb = view.findViewById(R.id.tvVerb)
        tvRound = view.findViewById(R.id.tvRoundCount)

        initButtons(view)


        activityViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[MainActivityViewModel::class.java]

        fragmentViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[AntonymsTestFragmentViewModel::class.java]

        setOnToolbarBackArrowPressedNavigation() // here?

        activityViewModel.dataList
            .observe(viewLifecycleOwner) { data ->

                if (fragmentViewModel.isItTheFirstGame()) {
                    fragmentViewModel.loadNewRound(data)
                }
                restoreRound()
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
                    if (isAnswerRight(button.text)) {
                        fragmentViewModel.onAnsweringRight(data)
                        restoreRound()
                    } else {
                        button.setBackgroundColor(Color.RED)
                    }
                }
            )
        }
    }

    private fun isAnswerRight(text: CharSequence) =
        text.toString() == fragmentViewModel.antonym.questionAndAnswerPair.second

    private fun setOnToolbarBackArrowPressedNavigation() {
        (activity as? AppCompatActivity)?.onBackPressedDispatcher?.addCallback {
            goBackOneRound()
            restoreRound()
        }
    }

    private fun goBackOneRound() {
        fragmentViewModel.getBackOneSet(activityViewModel.dataList.value ?: emptyList())
    }

    private fun restoreRound() {
        setCurrentWordTextView()
        setOptionButtons()
    }

    private fun setOptionButtons() {
        buttons.forEachIndexed { index, button ->
            button.text = fragmentViewModel.antonym.listOfAnswerOptions[index]

            setOnLongPressOptionWordNavigation(activityViewModel.dataList.value ?: emptyList())

            // todo save wrong picked options red colored
            button.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun setCurrentWordTextView() {
        // set text
        tvVerb.text = fragmentViewModel.antonym.questionAndAnswerPair.first

        val currentRoundPlayed = fragmentViewModel.currentRound.plus(1)
        tvRound.text =
            currentRoundPlayed.toString() + " / " + fragmentViewModel.getTotalNumberOfRounds()

        // set navigation
        tvVerb.setOnDoubleClickListener {
            //finds the right verb and let's us go to the second fragment with list of meanings
            val verb =
                activityViewModel.dataList.value?.find { it.writing == fragmentViewModel.antonym.questionAndAnswerPair.first }
            val bundle = Bundle()
            activityViewModel.dataList.value?.let {
                bundle.putInt(
                    "verbPosition",
                    it.indexOf(verb)
                )
            }

            findNavController().navigate(R.id.action_test_antonyms_to_SecondFragment, bundle)
        }
    }

    // get rid of this and do both longpress
    @SuppressLint("ClickableViewAccessibility")
    fun TextView.setOnDoubleClickListener(onDoubleClick: () -> Unit) {
        val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

                //maybe remove
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    onDoubleClick()
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    onDoubleClick()
                    super.onLongPress(e)
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