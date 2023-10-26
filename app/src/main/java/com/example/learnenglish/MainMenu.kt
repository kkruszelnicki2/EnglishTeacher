package com.example.learnenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainMenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_menu, container, false)

        val textView : TextView = view.findViewById<TextView>(R.id.userAuthority)

        var authorityLvl = this.arguments?.get("user")
        textView.text = authorityLvl.toString()

        view.findViewById<Button>(R.id.logOff).setOnClickListener {
            val fragment = LoginFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        return view
    }

}