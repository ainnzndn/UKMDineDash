package com.example.ukmdinedash.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ukmdinedash.adapters.FavsAdapter
import com.example.ukmdinedash.databinding.FragmentFavsBinding

class FavsFragment : Fragment() {

    private lateinit var binding: FragmentFavsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavsBinding.inflate(inflater,container,false)

        val favsFood = listOf(
            "Nasi Ayam Biasa", "Nasi Ayam Peha", "Nasi Ayam Hainan", "Nasi Ayam Madu", "Nasi Ayam Special"
            , "Nasi Ayam Penyet", "Nasi Ikan Keli Penyet", "Nasi Talapia Penyet", "Nasi Kukus Biasa", "Nasi Kukus Ayam Dara"
            , "Tomyam/Paprik/Beraneka", "Nasi Putih Berlauk", "Nasi Goreng/Beraneka", "Mee Goreng/Beraneka", "Mihun Goreng/Beraneka", "Kueyteow Goreng/Beraneka"
            , "Pasta/Spaghetti", "Chicken/Beef Lasagna", "Chicken Chop", "Beef Steak", "Lamb Chop", "Fish n Chips"
            , "Sirap/Teh O/Kopi O", "Teh/Kopi/Bandung", "Milo/Nescafe/Greentea", "Jus Buah", "ABC/Cendol"
            , "Creamy Strawberry", "Teh Ais Tarik", "Milky Honeydew", "Double Chocolate", "Kopi Ais Kaww", "Dreamy Vanilla Blue" )

        val favsPrice = listOf(
            "RM4.50", "RM5.50", "RM4.50", "RM5.50", "RM6.50"
            , "RM5.90", "RM6.90", "RM7.90", "RM5.90", "RM6.90"
            , "RM8.50", "RM8.00", "RM7.00", "RM6.50", "RM6.50", "RM6.50"
            , "RM6.50", "RM6.50", "RM7.00", "RM8.00", "RM8.50", "RM7.50"
            , "RM1.50", "RM2.00", "RM2.50", "RM3.00", "RM3.50"
            , "RM2.50", "RM2.50", "RM2.50", "RM2.50", "RM2.50", "RM2.50" )


        val adapter = FavsAdapter(favsFood.toMutableList(), favsPrice.toMutableList())
        binding.rvFavs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavs.adapter = adapter

        binding.btnClearall.setOnClickListener {
            val toastMessage = "Your Favourites are now empty!"
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
            val favsFood = mutableListOf<String>() // Create empty mutable lists
            val favsPrice = mutableListOf<String>()
            val adapter = FavsAdapter(favsFood, favsPrice)
            binding.rvFavs.layoutManager = LinearLayoutManager(requireContext())
            binding.rvFavs.adapter = adapter
        }
        return binding.root
    }

    companion object {

    }
}