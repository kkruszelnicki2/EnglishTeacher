package com.example.learnenglish

class Question (
    val question: String,
    val answer: String
)

abstract class QuestionList {
    open var answered : Int = 0
    abstract val questions: List<Question>

    abstract fun rollQuestion(): Question

    class QuestionsTransport: QuestionList() {
        override var answered : Int = 50

        override val questions: List<Question> = listOf(
            Question("Pociąg","train"),
            Question("Samochód","car"),
            Question("Samolot","airplane")
        )

        override fun rollQuestion(): Question {
            var questionNumber: Int = (0 until questions.count()).random()

            return questions[questionNumber]
        }
    }

    companion object {
        fun getQuestionList(list: String?): QuestionList {
            if(list.equals("Transport",true)) {
                return QuestionsTransport()
            }

            return QuestionsTransport()
        }
    }
}
