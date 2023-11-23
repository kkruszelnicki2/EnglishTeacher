package com.example.learnenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Levels : Fragment(), LevelClickListener {
    private lateinit var listener: LevelClickListener
    private lateinit var recyclerViewTransport: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.levels, container, false)

        listener = this

        recyclerViewTransport = view.findViewById(R.id.recycler)
        recyclerViewTransport.layoutManager = GridLayoutManager(context,10)
        recyclerViewTransport.adapter = QuestionAdapter(requireContext(),listener,
            QuestionList.QuestionsTransport(), "Transport"
        )

        return view
    }

    override fun onLevelClick(view: View,levelNumber: Int, levelName: String) {
        val fragment = LevelFragment()
        val transaction = fragmentManager?.beginTransaction()

        val bundle = Bundle()
        bundle.putInt("levelNumber",levelNumber)
        bundle.putString("levelName",levelName)
        fragment.arguments = bundle

        transaction?.replace(R.id.nav_controler, fragment)?.commit()
    }
}