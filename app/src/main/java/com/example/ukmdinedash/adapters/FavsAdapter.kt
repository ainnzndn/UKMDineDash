package com.example.ukmdinedash.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ukmdinedash.databinding.EachFavBinding

data class FavsAdapter(
    private val eachFav: MutableList<String>,
    private val favPrice: MutableList<String>
    ): RecyclerView.Adapter<FavsAdapter.FavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = EachFavBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return FavViewHolder(binding)

    }

    override fun getItemCount(): Int = eachFav.size

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class FavViewHolder(private val binding: EachFavBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                tvFoodnameFav.text = eachFav[position]
                tvFoodpriceFav.text = favPrice[position]

                delete2.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(position)
                    }
                }
            }
        }
        private fun deleteItem(position: Int) {
            eachFav.removeAt(position)
            favPrice.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, eachFav.size)
        }
    }
}
