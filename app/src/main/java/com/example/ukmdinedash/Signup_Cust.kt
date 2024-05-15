package com.example.ukmdinedash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ukmdinedash.databinding.CustSignupBinding
import com.example.ukmdinedash.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Signup_Cust : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String
    private lateinit var confirmpass: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: CustSignupBinding by lazy {
        CustSignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.SUbtnSignup.setOnClickListener {
            name = binding.SUetName.text.toString()
            email = binding.SUetEmail.text.toString().trim()
            password = binding.SUetPassword.text.toString().trim()
            confirmpass = binding.SUetConfirmpass.text.toString().trim()

            if (email.isEmpty() || password.isBlank() || name.isBlank() || confirmpass.isBlank()) {
                Toast.makeText(this, "Please ensure all fields are completed", Toast.LENGTH_SHORT).show()
            }

            else if (!email.endsWith("@siswa.ukm.edu.my") && !email.endsWith("@ukm.edu.my")) {
                Toast.makeText(this, "Please use a valid UKM Email Address", Toast.LENGTH_SHORT).show()
            }

            else if (password != confirmpass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
            else {
                signUp(email, password)
            }
        }

        binding.SUtvLogin.setOnClickListener {
            val intent = Intent(this, Login_Customer::class.java)
            startActivity(intent)
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                saveUserData()
                intent.putExtra("userName", name)
                intent.putExtra("userEmail", email)
                startActivity(Intent(this, Login_Customer::class.java))
                finish()
            } else {
                Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "signUp: Failure", task.exception)
            }
        }
    }

    private fun saveUserData() {
        val users = UserModel(name, email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("users").child(it).setValue(users)
        }
    }
}
