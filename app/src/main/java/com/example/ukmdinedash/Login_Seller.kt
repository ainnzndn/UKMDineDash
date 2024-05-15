package com.example.ukmdinedash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ukmdinedash.databinding.SellerLoginBinding
import com.example.ukmdinedash.model.SellerModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Login_Seller : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference

    private val binding: SellerLoginBinding by lazy {
        SellerLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.SEbtnLogin.setOnClickListener {

            email = binding.SEetEmail.text.toString().trim()
            password = binding.SEetPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please ensure all fields are completed", Toast.LENGTH_SHORT).show()
            }
            else if (!email.endsWith("@ukmdinedash.my")) {
                Toast.makeText(this, "Please use a valid UKM Email Address", Toast.LENGTH_SHORT).show()
            }
            else {
                createSeller()
            }
        }

        binding.SEbtnLogincust.setOnClickListener {
            val intent = Intent(this, Login_Customer::class.java)
            startActivity(intent)
        }

        binding.SEbtnLoginadmin.setOnClickListener {
            val intent = Intent(this, Login_Admin::class.java)
            startActivity(intent)
        }
    }

        private fun createSeller() {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val seller = auth.currentUser
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    updateUI(seller)
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUserData()
                            val seller: FirebaseUser? = auth.currentUser
                            Toast.makeText(this, "Seller Login Successful", Toast.LENGTH_SHORT).show()
                            updateUI(seller)
                        } else {
                            Toast.makeText(this, "Seller Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        private fun saveUserData() {
            email = binding.SEetEmail.text.toString().trim()
            password = binding.SEetPassword.text.toString().trim()

            val seller = SellerModel(email, password)
            val sellerId = FirebaseAuth.getInstance().currentUser!!.uid

            database.child("seller").child(sellerId).setValue(seller)
        }
        private fun updateUI(user: FirebaseUser?) {
            val intent = Intent (this, Seller_Menu::class.java)
            startActivity(intent)
            finish()
    }
}