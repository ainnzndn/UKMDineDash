package com.example.ukmdinedash.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ukmdinedash.Cust_FoodMenu
import com.example.ukmdinedash.databinding.EachStallBinding

class StallAdapter(
    private val stallList: List<Pair<String, String>>,
    private val collegeName: String?, // Add collegeName parameter
    private val context: Context
) : RecyclerView.Adapter<StallAdapter.StallViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallViewHolder {
        val binding = EachStallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StallViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stallList.size
    }

    override fun onBindViewHolder(holder: StallViewHolder, position: Int) {
        val (stallName, imageUrl) = stallList[position]
        holder.bind(stallName, imageUrl)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, Cust_FoodMenu::class.java)
            intent.putExtra("collegeName", collegeName)
            intent.putExtra("stallName", stallName)

            // You may want to pass imageUrl to the next activity as well
            intent.putExtra("stallImageUrl", imageUrl)

            context.startActivity(intent)
        }
    }

    inner class StallViewHolder(private val binding: EachStallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stallName: String, imageUrl: String) {
            binding.tvStallname.text = stallName
            // Load image using Glide or Picasso library
            Glide.with(context).load(imageUrl).into(binding.imgStall)
        }
    }
}
