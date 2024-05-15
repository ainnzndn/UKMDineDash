package com.example.ukmdinedash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ukmdinedash.adapters.FoodAdapter
import com.example.ukmdinedash.adapters.FoodItemClickListener
import com.example.ukmdinedash.databinding.CustFoodMenuBinding
import com.example.ukmdinedash.fragments.CartFragment
import com.example.ukmdinedash.fragments.FavsFragment
import com.example.ukmdinedash.fragments.HomeFragment
import com.example.ukmdinedash.fragments.ProfileFragment
import com.example.ukmdinedash.model.FoodItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

@Suppress("DEPRECATION")
class Cust_FoodMenu : AppCompatActivity(), FoodItemClickListener {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: CustFoodMenuBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var foodItems: MutableList<FoodItem>

    private val STALLS_PATH = "stalls"
    private val FOODS_PATH = "foodS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = CustFoodMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve and set college and stall names from the Intent
        val selectedCollegeName = intent.getStringExtra("collegeName")
        binding.tvCollegename3.text = selectedCollegeName

        val selectedStallName = intent.getStringExtra("stallName")
        binding.tvStallname2.text = selectedStallName

        // Initialize Firebase database
        initFirebaseDatabase()

        // Retrieve and display food items
        fetchFoodItemsFromFirebase(selectedStallName)

        // Initialize BottomNavigationView
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.visibility = View.GONE

        // Set up Floating Action Button to show the BottomNavigationView and handle navigation
        val buttonCart = binding.fabCart
        buttonCart.setOnClickListener {
            bottomNavigationView.visibility = View.VISIBLE
            setupBottomNavigationView()

            // Set the initial selected fragment
            bottomNavigationView.selectedItemId = R.id.cartFragment
            replaceFragment(CartFragment())
        }
    }

    private fun initFirebaseDatabase() {
        database = FirebaseDatabase.getInstance()
    }

    private fun fetchFoodItemsFromFirebase(selectedStallName: String?) {
        val foodRef: DatabaseReference = database.reference.child(STALLS_PATH).child(selectedStallName?: "").child(FOODS_PATH)
        foodItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val foodItem = foodSnapshot.getValue(FoodItem::class.java)
                    foodItem?.let {
                        foodItems.add(it)
                    }
                }
                Log.d("Items", "onDataChange: Data Received")
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Items", "onCancelled: Error fetching data", error.toException())
            }
        })
    }

    private fun setAdapter() {
        val adapter = FoodAdapter(foodItems, this)
        binding.rvFoodmenu.layoutManager = LinearLayoutManager(this)
        binding.rvFoodmenu.adapter = adapter?: FoodAdapter(emptyList(), this)
        Log.d("Items", "setAdapter: Set")
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.cartFragment -> replaceFragment(CartFragment())
                R.id.favsFragment -> replaceFragment(FavsFragment())
                R.id.profileFragment -> replaceFragment(ProfileFragment())
                else -> false
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container2, fragment)
            .commit()
    }

    override fun onFoodItemClicked(foodItem: FoodItem) {
        // Handle the food item click here, for example, by showing a fragment or updating the UI
        val fragment = CartFragment().apply {
            arguments = Bundle().apply {
                putString("foodName", foodItem.itemName)
                putString("foodPrice", foodItem.itemPrice)
            }
        }
        replaceFragment(fragment)
    }
}
