package com.example.ukmdinedash.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ukmdinedash.databinding.EachCartBinding

class CartAdapter(
    private val eachCart: MutableList<String>,
    private val cartPrice: MutableList<String>
    ): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val itemQuantity = IntArray(eachCart.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = EachCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = eachCart.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CartViewHolder(private val binding: EachCartBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantity[position]
                tvFood.text = eachCart[position]
                tvPrice.text = cartPrice[position]
                tvQuantity.text= quantity.toString()

                minus.setOnClickListener {
                    decreaseQuantity(position)
                }

                plus.setOnClickListener {
                    increaseQuantity(position)
                }

                delete.setOnClickListener {
                    val itemPosition = adapterPosition
                        if (itemPosition != RecyclerView.NO_POSITION) {
                            deleteItem(position)
                    }
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantity[position]<10) {
                itemQuantity[position]++
                binding.tvQuantity.text = itemQuantity[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantity[position]>1) {
                itemQuantity[position]--
                binding.tvQuantity.text = itemQuantity[position].toString()
            }
        }

        private fun deleteItem(position: Int) {
            eachCart.removeAt(position)
            cartPrice.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, eachCart.size)
        }

    }
}