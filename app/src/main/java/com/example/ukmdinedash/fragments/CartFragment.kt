package com.example.ukmdinedash.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ukmdinedash.TrackOrder
import com.example.ukmdinedash.adapters.CartAdapter
import com.example.ukmdinedash.databinding.FragmentCartBinding
import com.example.ukmdinedash.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var dateCart: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        dateCart = binding.tvCartDate

        val cartFood = listOf(
            "Nasi Ayam Biasa", "Nasi Ayam Peha", "Nasi Ayam Hainan", "Nasi Ayam Madu", "Nasi Ayam Special"
            , "Nasi Ayam Penyet", "Nasi Ikan Keli Penyet", "Nasi Talapia Penyet", "Nasi Kukus Biasa", "Nasi Kukus Ayam Dara"
            , "Tomyam/Paprik/Beraneka", "Nasi Putih Berlauk", "Nasi Goreng/Beraneka", "Mee Goreng/Beraneka", "Mihun Goreng/Beraneka", "Kueyteow Goreng/Beraneka"
            , "Pasta/Spaghetti", "Chicken/Beef Lasagna", "Chicken Chop", "Beef Steak", "Lamb Chop", "Fish n Chips"
            , "Sirap/Teh O/Kopi O", "Teh/Kopi/Bandung", "Milo/Nescafe/Greentea", "Jus Buah", "ABC/Cendol"
            , "Creamy Strawberry", "Teh Ais Tarik", "Milky Honeydew", "Double Chocolate", "Kopi Ais Kaww", "Dreamy Vanilla Blue" )

        val cartPrice = listOf(
            "RM4.50", "RM5.50", "RM4.50", "RM5.50", "RM6.50"
            , "RM5.90", "RM6.90", "RM7.90", "RM5.90", "RM6.90"
            , "RM8.50", "RM8.00", "RM7.00", "RM6.50", "RM6.50", "RM6.50"
            , "RM6.50", "RM6.50", "RM7.00", "RM8.00", "RM8.50", "RM7.50"
            , "RM1.50", "RM2.00", "RM2.50", "RM3.00", "RM3.50"
            , "RM2.50", "RM2.50", "RM2.50", "RM2.50", "RM2.50", "RM2.50" )

        val adapter = CartAdapter(ArrayList(cartFood), ArrayList(cartPrice))
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = adapter
        binding.btnProceed.setOnClickListener {
            val intent = Intent(requireContext(), TrackOrder::class.java)
            startActivity(intent)
        }

        val currentDateTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDateTime)
        dateCart.text = formattedDate

        return binding.root
    }

    companion object {
    }
}