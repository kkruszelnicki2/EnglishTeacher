package com.example.learnenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainMenu : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData() //load data from Firestore

        view.findViewById<Button>(R.id.start).setOnClickListener {//go to level list button
            val fragment = Levels()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        view.findViewById<Button>(R.id.settings).setOnClickListener {//settings button
            val fragment = Settings()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        view.findViewById<Button>(R.id.logOff).setOnClickListener {//logoff button
            FirebaseAuth.getInstance().signOut()

            val fragment = LoginFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_controler, fragment)?.commit()
        }

        view.findViewById<Button>(R.id.register).setOnClickListener {//register anonymous account button
            val email = view.findViewById<EditText>(R.id.inputEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.inputPassword).text.toString()
            val rPassword = view.findViewById<EditText>(R.id.inputPasswordRepeat).text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && rPassword.isNotEmpty()) { //if email and passwords brackets aren't empty
                if(password == rPassword) { //if passwords match
                    if(isValidEmail(email)) { //if email is in correct format (simple check, only demands @)
                        var credential = EmailAuthProvider.getCredential(email,password)
                        firebaseAuth.currentUser!!.linkWithCredential(credential).addOnCompleteListener {
                            if(it.isSuccessful) { //if successfully created account
                                val fragment = LoginFragment()
                                val transaction = fragmentManager?.beginTransaction()

                                transaction?.replace(R.id.nav_controler, fragment)?.commit()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean { //check if email is in correct format (@)
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

    private fun loadData() { //Loading data from FireStore (if exists)
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser?.uid

        val usersRef = db.collection("users").document("$user")

        usersRef.get().addOnCompleteListener { task->
            if(task.isSuccessful) {
                val document = task.result

                if (document.exists()) {
                    val cloudData = document.data

                    if (cloudData != null) {
                        if(cloudData["transport_level"] != null)
                            QuestionList.QuestionsTransport.loadData(cloudData["transport_level"].toString().toInt())

                        if(cloudData["food_level"] != null)
                            QuestionList.QuestionsFood.loadData(cloudData["food_level"].toString().toInt())
                    }
                }
            }
        }
    }
}