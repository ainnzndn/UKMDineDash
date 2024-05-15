package com.example.ukmdinedash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ukmdinedash.databinding.AdminLoginBinding
import com.example.ukmdinedash.model.SellerModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Login_Admin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference

    private val binding: AdminLoginBinding by lazy {
        AdminLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.ADbtnLogin.setOnClickListener {
            email = binding.ADetEmail.text.toString().trim()
            password = binding.ADetPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please ensure all fields are completed", Toast.LENGTH_SHORT).show()
            }

            else if (!email.endsWith("@ukmdinedash.my")) {
                Toast.makeText(this, "Please use a valid UKM Email Address", Toast.LENGTH_SHORT).show()
            }

            else {
                createAdmin()
            }
        }

        binding.ADbtnLogincust.setOnClickListener {
            val intent = Intent(this, Login_Customer::class.java)
            startActivity(intent)
        }
        binding.ADbtnLoginseller.setOnClickListener {
            val intent = Intent(this, Login_Seller::class.java)
            startActivity(intent)
        }
    }

    private fun createAdmin() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val admin = auth.currentUser
                Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show()
                updateUI(admin)
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserData()
                        val admin: FirebaseUser? = auth.currentUser
                        Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show()
                        updateUI(admin)
                    } else {
                        Toast.makeText(this, "Admin Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email = binding.ADetEmail.text.toString().trim()
        password = binding.ADetPassword.text.toString().trim()

        val admin = SellerModel(email, password)
        val adminId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("admin").child(adminId).setValue(admin)
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent (this, Admin_Menu::class.java)
        startActivity(intent)
        finish()
    }
}