package com.example.learnenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class Settings : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings, container, false)

        auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.goBack).setOnClickListener {
            val fragment = MainMenu()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }
        return view
    }

}