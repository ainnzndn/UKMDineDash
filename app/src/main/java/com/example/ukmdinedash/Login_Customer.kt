package com.example.ukmdinedash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ukmdinedash.databinding.CustLoginBinding
import com.example.ukmdinedash.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Login_Customer : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var name: String?=null
    private lateinit var email: String
    private lateinit var password: String

    private val binding: CustLoginBinding by lazy {
        CustLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.LGtvSignup.setOnClickListener {
            val intent = Intent(this, Signup_Cust::class.java)
            startActivity(intent)
        }

        binding.LGbtnLogin.setOnClickListener {

            email = binding.LGetEmail.text.toString().trim()
            password = binding.LGetPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please ensure all fields are completed", Toast.LENGTH_SHORT).show()
            }

            else if (!email.endsWith("@siswa.ukm.edu.my") && !email.endsWith("@ukm.edu.my")) {
                Toast.makeText(this, "Please use a valid UKM Email Address", Toast.LENGTH_SHORT).show()
            }

            else {
                createUser()
            }

        }

        binding.LGbtnAdminseller.setOnClickListener {
            val intent = Intent(this, Login_Admin::class.java)
            startActivity(intent)
        }
    }

    private fun createUser() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val users = auth.currentUser
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                updateUI(users)
            } else {
                Toast.makeText(this, "Oops! It seems this account isn't registered yet.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData() {
        email = binding.LGetEmail.text.toString().trim()
        password = binding.LGetPassword.text.toString().trim()

        val users = UserModel(name, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("users").child(userId).setValue(users)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, Cust_MainMenu::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent (this, Cust_MainMenu::class.java)
        startActivity(intent)
        finish()
    }
}
