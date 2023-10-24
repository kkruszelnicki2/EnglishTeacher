package com.example.learnenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register_fragment, container, false)

        view.findViewById<Button>(R.id.register).setOnClickListener {
            if(view.findViewById<EditText>(R.id.inputPasswordText).text.toString() == view.findViewById<EditText>(R.id.repeatPasswordText).text.toString()) {
                val bundle = Bundle()
                bundle.putInt("user",1)

                val fragment = MainMenu()
                val transaction = fragmentManager?.beginTransaction()
                fragment.arguments = bundle

                transaction?.replace(R.id.nav_controler, fragment)?.commit()
            }
        }

        view.findViewById<Button>(R.id.backToLogin).setOnClickListener {
            val fragment = LoginFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        return view
    }

}