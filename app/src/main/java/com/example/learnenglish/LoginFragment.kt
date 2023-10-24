package com.example.learnenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        val registerView: TextView = view.findViewById<TextView>(R.id.regsiterView)
        registerView.setOnClickListener{
            val fragment = RegisterFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        val guest: TextView = view.findViewById<TextView>(R.id.guestLogin)
        guest.setOnClickListener{
            val fragment = MainMenu()
            val transaction = fragmentManager?.beginTransaction()

            val bundle = Bundle()
            bundle.putInt("user",0)
            fragment.arguments = bundle

            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        view.findViewById<Button>(R.id.login).setOnClickListener {
            login()
        }

        return view
    }

    fun login() {
        val bundle = Bundle()
        bundle.putInt("user",1)

        val fragment = MainMenu()
        fragment.arguments = bundle

        fragmentManager?.beginTransaction()?.replace(R.id.nav_controler, fragment)?.commit()
    }
}