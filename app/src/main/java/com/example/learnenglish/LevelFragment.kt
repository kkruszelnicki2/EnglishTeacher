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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaskModel(val transport_level : Int, val food_level: Int)

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

        view.findViewById<Button>(R.id.leave).setOnClickListener {//return to main menu button
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Na pewno chcesz wyjść? Niezapisane postępy zostaną utracone!") //notification reminding user, that unsaved data will be lost
                .setPositiveButton("Tak") { _, _ -> //if user pressed yes
                    val fragment = MainMenu()
                    val transaction = fragmentManager?.beginTransaction()

                    transaction?.replace(R.id.nav_controler, fragment)?.commit() //move him to main menu fragment
                }
                .setNegativeButton("Nie") { dialog, _ -> //if user pressed no
                    dialog.dismiss() //just hide notification
                }
            val alert = builder.create()
            alert.show()
        }

        //get data from previous fragment
        levelNumber = this.arguments?.getInt("levelNumber")
        var levelName: String? = this.arguments?.getString("levelName")
        questionsList = QuestionList.getQuestionList(levelName)

        //and use it to create question
        updateLevelName()
        createQuestion()
    }

    private fun updateLevelName() { //update level name
        val levelTitle: TextView? = view?.findViewById<TextView>(R.id.levelNumber)

        levelTitle?.text = "Level: $levelNumber"
    }

    private fun createQuestion() { //Get new random question
        val questionView: TextView? = view?.findViewById<TextView>(R.id.question)

        question = questionsList.rollQuestion()

        if (questionView != null) {
            questionView.text = question.question
        }
    }

    private fun checkAnswer() { //Check if answer is correct
        val userAnswer: EditText? = view?.findViewById<EditText>(R.id.inputAnswer)

        if(question.answer == userAnswer?.text.toString().toLowerCase()) { //if answer is correct
            levelNumber = levelNumber?.plus(1) //update level number
            updateLevelName() //update level name
            createQuestion() //get new question randomly
            userAnswer?.text?.clear() //clear answer input
        }

        if(levelNumber?.rem(10) == 0) {
            if(levelNumber!! > questionsList.getAnswered()) {
                questionsList.setAnswered(levelNumber as Int)
                saveData()
            }
        }
    }

    private fun saveData() { //Save data with Firestore Database
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser?.uid

        val usersRef = db.collection("users").document("$user")

        usersRef.get().addOnCompleteListener { task->
            if(task.isSuccessful) {
                val document = task.result

//                if(document.exists()) { //if this user's document exists just save data
//                    usersRef.update("transport_level",levelNumber)
//                }
//                else { //else create document and save data
                val data = TaskModel(QuestionList.QuestionsTransport.getData(),
                    QuestionList.QuestionsFood.getData())
                db.collection("users").document("$user").set(data)
//                }
            }
        }

    }
}