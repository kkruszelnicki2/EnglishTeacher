package com.example.learnenglish

class Question ( //question constructor
    val question: String,
    val answer: String
)

abstract class QuestionList { //lists of questions
    abstract fun getAnswered(): Int //get amount of answered questions
    abstract fun setAnswered(newVal: Int) //set amount of answered questions (used for uploading data from Firestore)

    abstract val questions: List<Question> //list of questions with answers (Question class)

    fun rollQuestion(): Question { //get random question from questions list
        var questionNumber: Int = (0 until questions.count()).random()

        return questions[questionNumber]
    }

    companion object {
        fun getQuestionList(list: String?): QuestionList { //get certain questions list
            if(list.equals("Transport",true)) {
                return QuestionsTransport()
            }
            else if(list.equals("Food",true)) {
                return QuestionsFood()
            }

            return QuestionsTransport() //if any bug appears, just let user play with transport questions
        }
    }

    class QuestionsTransport: QuestionList() { //Transport related questions
        override fun getAnswered(): Int {
            return answered
        }

        override fun setAnswered(newVal: Int){
            answered = newVal
        }

        override val questions: List<Question> = listOf(
            Question("Pociąg","train"),
            Question("Samochód","car"),
            Question("Samolot","airplane")
        )

        companion object {
            fun loadData(newAnswered: Int) {
                answered = newAnswered
            }

            fun getData(): Int {
                return answered
            }

            var answered: Int = 0
        }
    }

    class QuestionsFood: QuestionList() { //Transport related questions
        override fun getAnswered(): Int {
            return answered
        }

        override fun setAnswered(newVal: Int){
            answered = newVal
        }

        override val questions: List<Question> = listOf(
            Question("Banan","banana"),
            Question("Jabłko","apple"),
            Question("Ser","cheese")
        )

        companion object {
            fun loadData(newAnswered: Int) {
                answered = newAnswered
            }

            fun getData(): Int {
                return answered
            }

            var answered: Int = 0
        }
    }
}
