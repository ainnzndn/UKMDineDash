package com.example.ukmdinedash.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ukmdinedash.databinding.EachCollegeBinding
import com.example.ukmdinedash.model.CollegeModel

class CollegeAdapter(
    private val colleges: List<CollegeModel>,
    private val context: Context
) : RecyclerView.Adapter<CollegeAdapter.CollegeViewHolder>() {

    private var onItemClickListener: ((CollegeModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (CollegeModel) -> Unit) {
        onItemClickListener = listener
    }

    inner class CollegeViewHolder(private val binding: EachCollegeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(college: CollegeModel) {
            binding.tvCollege.text = college.name
            // Load image with Glide
            Glide.with(context)
                .load(college.image)
                .into(binding.imgCollege)

            binding.root.setOnClickListener {
                onItemClickListener?.let { it(college) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollegeViewHolder {
        val binding = EachCollegeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollegeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollegeViewHolder, position: Int) {
        val college = colleges[position]
        holder.bind(college)
    }

    override fun getItemCount(): Int {
        return colleges.size
    }
}
