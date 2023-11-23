package com.example.learnenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainMenu : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_menu, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        var authorityLvl = this.arguments?.get("user")

        val currentUser = firebaseAuth.currentUser

//        if (currentUser != null) {
//            textView.text = currentUser.isAnonymous.toString()
//            if(currentUser.isAnonymous) {
//                textView.text = "guest"
//                view.findViewById<ConstraintLayout>(R.id.createAcc).visibility = View.VISIBLE
//            }
//        }

        view.findViewById<Button>(R.id.start).setOnClickListener {
            val fragment = Levels()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        view.findViewById<Button>(R.id.settings).setOnClickListener {
            val fragment = Settings()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        view.findViewById<Button>(R.id.logOff).setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val fragment = LoginFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        view.findViewById<Button>(R.id.register).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.inputEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.inputPassword).text.toString()
            val rPassword = view.findViewById<EditText>(R.id.inputPasswordRepeat).text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && rPassword.isNotEmpty()) {
                if(password == rPassword) {
                    if(isValidEmail(email)) {
                        var credential = EmailAuthProvider.getCredential(email,password)
                        firebaseAuth.currentUser!!.linkWithCredential(credential).addOnCompleteListener {
                            if(it.isSuccessful) {
                                view.findViewById<Button>(R.id.register).text = "abc"
                                val firebaseUser: FirebaseUser = it.result!!.user!!

                                val bundle = Bundle()
                                bundle.putString("user", firebaseUser.uid)
                                bundle.putString("email", email)

                                val fragment = LoginFragment()
                                val transaction = fragmentManager?.beginTransaction()
                                fragment.arguments = bundle

                                transaction?.replace(R.id.nav_controler, fragment)?.commit()
                            }
                        }
                    }
                }
            }
        }

        return view
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

}