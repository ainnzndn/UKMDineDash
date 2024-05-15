package com.example.ukmdinedash

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ukmdinedash.databinding.CustStallMenuBinding
import com.example.ukmdinedash.adapters.StallAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Cust_StallMenu : AppCompatActivity() {
    private lateinit var binding: CustStallMenuBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustStallMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the college name from the intent extras
        val collegeStallName = intent.getStringExtra("collegeName")

        // Check if collegeStallName is not null before setting the text
        collegeStallName?.let {
            binding.tvCollegename2.text = it
        }

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("stalls")

        // Retrieve data from Firebase Database
        val stallList = mutableListOf<Pair<String, String>>() // Pair of stall name and image URL
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (stallSnapshot in snapshot.children) {
                    val stallName = stallSnapshot.child("nameS").value.toString()
                    val stallImage = stallSnapshot.child("imageS").value.toString()
                    stallList.add(Pair(stallName, stallImage))
                }
                // Pass the retrieved data to the adapter
                val adapter = StallAdapter(stallList, collegeStallName, this@Cust_StallMenu)
                binding.rvStall.layoutManager = LinearLayoutManager(this@Cust_StallMenu)
                binding.rvStall.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}

