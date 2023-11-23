package com.example.learnenglish

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(
    private val context: Context,
    private val levelClickListener: LevelClickListener,
    private val question: QuestionList,
    private val listName: String
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionAdapter.QuestionViewHolder {
        return QuestionViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.question_row,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: QuestionAdapter.QuestionViewHolder, position: Int) {
        holder.questionIndex.text = (position * 10).toString()

        holder.questionIndex.setOnClickListener {
            levelClickListener.onLevelClick(it, (holder.questionIndex.text as String).toInt(), listName)
        }
    }

    override fun getItemCount() = question.answered/10

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionIndex: TextView = itemView.findViewById(R.id.index)
    }

}