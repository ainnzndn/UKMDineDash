package com.example.ukmdinedash.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ukmdinedash.databinding.EachFoodBinding
import com.example.ukmdinedash.model.FoodItem

class FoodAdapter(private val foodItems: List<FoodItem>, private val context: Context)
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = EachFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodItems.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodItems[position])
    }

    inner class FoodViewHolder(private val binding: EachFoodBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openCartFragment(position)
                }
            }
        }

        private fun openCartFragment(position: Int) {
            val foodItem = foodItems[position]
            (context as? FoodItemClickListener)?.onFoodItemClicked(foodItem)
        }

        fun bind(foodItem: FoodItem) {
            Log.d("Adapter", "Binding food item: ${foodItem.itemName}, ${foodItem.itemPrice}")
            binding.apply {
                tvFoodname.text = foodItem.itemName
                tvFoodprice.text = foodItem.itemPrice
            }
        }
    }
}

interface FoodItemClickListener {
    fun onFoodItemClicked(foodItem: FoodItem)
}
