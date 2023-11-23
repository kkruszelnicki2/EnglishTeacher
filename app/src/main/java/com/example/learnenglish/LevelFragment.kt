package com.example.learnenglish

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class LevelFragment : Fragment() {

    private lateinit var questionsList : QuestionList
    private lateinit var question: Question
    private var levelNumber: Int? = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.level_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.answer).setOnClickListener {
            checkAnswer()
        }

        view.findViewById<Button>(R.id.leave).setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Na pewno chcesz wyjść? Niezapisane postępy zostaną utracone!")
                .setPositiveButton("Tak") { _, _ ->
                    val fragment = MainMenu()
                    val transaction = fragmentManager?.beginTransaction()

                    transaction?.replace(R.id.nav_controler, fragment)?.commit()
                }
                .setNegativeButton("Nie") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        levelNumber = this.arguments?.getInt("levelNumber")
        var levelName: String? = this.arguments?.getString("Transport")
        questionsList = QuestionList.getQuestionList(levelName)

        updateLevelName()
        createQuestion()
    }

    private fun updateLevelName() {
        val levelTitle: TextView? = view?.findViewById<TextView>(R.id.levelNumber)

        levelTitle?.text = "Level: $levelNumber"
    }

    private fun createQuestion() {
        val questionView: TextView? = view?.findViewById<TextView>(R.id.question)

        question = questionsList.rollQuestion()

        if (questionView != null) {
            questionView.text = question.question
        }
    }

    private fun checkAnswer() {
        val userAnswer: EditText? = view?.findViewById<EditText>(R.id.inputAnswer)
        val textView: TextView? = view?.findViewById<TextView>(R.id.levelNumber)

        if(question.answer == userAnswer?.text.toString().toLowerCase()) {
            levelNumber = levelNumber?.plus(1)
            updateLevelName()
            createQuestion()
            userAnswer?.text?.clear()
        }

        if(levelNumber?.rem(10) == 0) {
            if(levelNumber!! > questionsList.answered) {
                questionsList.answered = levelNumber as Int
                if (userAnswer != null) {
                    textView?.text = questionsList.answered.toString()
                }
            }
        }
    }
}