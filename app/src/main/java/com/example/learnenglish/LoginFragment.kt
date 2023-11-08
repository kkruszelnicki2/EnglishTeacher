package com.example.learnenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        val error = view.findViewById<TextView>(R.id.errorView)

        val registerView: TextView = view.findViewById<TextView>(R.id.regsiterView)
        registerView.setOnClickListener{
            val fragment = RegisterFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        val guest: TextView = view.findViewById<TextView>(R.id.guestLogin)
        guest.setOnClickListener{
            firebaseAuth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val fragment = MainMenu()
                        val transaction = fragmentManager?.beginTransaction()

                        val bundle = Bundle()
                        bundle.putInt("user", 0)
                        fragment.arguments = bundle

                        transaction?.replace(R.id.nav_controler, fragment)?.commit()
                    } else {
                        error.text = "Coś poszło nie tak."
                    }
                }
        }

        view.findViewById<Button>(R.id.login).setOnClickListener {
            var email = view.findViewById<EditText>(R.id.inputEmailText).text.toString()
            var password = view.findViewById<EditText>(R.id.inputPasswordText).text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val firebaseUser: FirebaseUser = it.result!!.user!!

                        val bundle = Bundle()
                        bundle.putString("user", firebaseUser.uid)
                        bundle.putString("email", email)

                        val fragment = MainMenu()
                        fragment.arguments = bundle

                        fragmentManager?.beginTransaction()?.replace(R.id.nav_controler, fragment)?.commit()
                    }
                    else {
                        error.text = "Nie ma takiego użytkownika. Sprawdź swój email i hasło."
                    }
                }
            }
            else {
                error.text = "Uzupełnij wszystkie pola!"
            }
        }

        return view
    }

}