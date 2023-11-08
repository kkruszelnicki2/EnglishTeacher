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

class RegisterFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register_fragment, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        val hasAccount: TextView = view.findViewById<TextView>(R.id.loginTransport)
        hasAccount.setOnClickListener{
            val fragment = LoginFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        view.findViewById<Button>(R.id.register).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.inputEmailText).text.toString()
            val password = view.findViewById<EditText>(R.id.inputPasswordText).text.toString()
            val rPassword = view.findViewById<EditText>(R.id.repeatPasswordText).text.toString()
            val error = view.findViewById<TextView>(R.id.errorView)

            if(email.isNotEmpty() && password.isNotEmpty() && rPassword.isNotEmpty()) {
                if(password == rPassword) {
                    if(isValidEmail(email)) {
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                            if(it.isSuccessful) {
                                val firebaseUser: FirebaseUser = it.result!!.user!!

                                val bundle = Bundle()
                                bundle.putString("user", firebaseUser.uid)
                                bundle.putString("email", email)

                                val fragment = MainMenu()
                                val transaction = fragmentManager?.beginTransaction()
                                fragment.arguments = bundle

                                transaction?.replace(R.id.nav_controler, fragment)?.commit()
                            }
                        }
                    }
                    else {
                        error.text = "Adres email jest niepoprawny!"
                    }
                }
                else {
                    error.text = "Hasła się różnią!"
                }
            }
            else {
                error.text = "Uzupełnij wszystkie pola!"
            }
        }

        view.findViewById<Button>(R.id.backToLogin).setOnClickListener {
            val fragment = LoginFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        return view
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

}