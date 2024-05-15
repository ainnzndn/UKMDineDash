package com.example.ukmdinedash.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ukmdinedash.Login_Customer
import com.example.ukmdinedash.databinding.FragmentProfileBinding
import com.example.ukmdinedash.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var nameProfile: TextView
    private lateinit var emailProfile: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        nameProfile = binding.tvProfileName
        emailProfile = binding.tvProfileEmail

        binding.logoutButton.setOnClickListener {
            val intent = Intent(requireContext(), Login_Customer::class.java)
            startActivity(intent)
        }
        return binding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            auth = FirebaseAuth.getInstance()
            val userId = auth.currentUser?.uid
            database = FirebaseDatabase.getInstance().reference.child("users").child(userId!!)

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(UserModel::class.java)
                        if (user != null) {
                            val name = user.name
                            val email = user.email
                            "$name".also { nameProfile.text = it }
                            "$email".also { emailProfile.text = it }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
