package com.ecs198f.foodtrucks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecs198f.foodtrucks.databinding.FoodTruckReviewsBinding

class FoodTruckReviewsRecyclerViewAdapter (private val reviews: List<FoodTruckReviews>): RecyclerView.Adapter<FoodTruckReviewsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: FoodTruckReviewsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FoodTruckReviewsBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        reviews[position].let {
            holder.binding.apply {

                foodTruckReviewName.text = it.authorName
                Glide.with(holder.itemView.context).load(it.authorAvatarUrl).into(foodTruckReviewAvatar)
                foodTruckReviewContent.text = it.content
            }
        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}
