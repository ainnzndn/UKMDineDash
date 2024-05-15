package com.example.ukmdinedash.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ukmdinedash.Cust_StallMenu
import com.example.ukmdinedash.adapters.CollegeAdapter
import com.example.ukmdinedash.databinding.FragmentHomeBinding
import com.example.ukmdinedash.model.CollegeModel
import com.example.ukmdinedash.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var nameCust: TextView
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        nameCust = binding.nameCust // Initialize nameCust here

        fetchUserData()
        fetchCollegesData()
    }

    private fun fetchUserData() {
        val userId = auth.currentUser!!.uid

        database.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(UserModel::class.java)
                    if (user != null) {
                        val name = user.name
                        nameCust.text = "$name!"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database read errors
            }
        })
    }

    private fun fetchCollegesData() {
        val collegesRef = database.child("colleges")
        collegesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val collegesList = mutableListOf<CollegeModel>()
                for (collegeSnapshot in snapshot.children) {
                    val college = collegeSnapshot.getValue(CollegeModel::class.java)
                    if (college != null) {
                        collegesList.add(college)
                    }
                }
                setupRecyclerView(collegesList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database read errors
            }
        })
    }

    private fun setupRecyclerView(colleges: List<CollegeModel>) {
        val adapter = CollegeAdapter(colleges, requireContext())
        binding.rvCollege.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCollege.adapter = adapter

        adapter.setOnItemClickListener { college ->
            openCustStallMenuActivity(college.name)
        }
    }

    private fun openCustStallMenuActivity(collegeName: String) {
        val intent = Intent(requireContext(), Cust_StallMenu::class.java)
        intent.putExtra("collegeName", collegeName)
        startActivity(intent)
    }
}
