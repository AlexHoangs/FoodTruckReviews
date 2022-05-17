package com.alexanderhoang.foodtruck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.alexanderhoang.foodtruck.databinding.FragmentFoodTruckReviewsListItemBinding

class FoodTruckReviewsRecyclerViewAdapter (private var reviews: List<FoodTruckReviews>):
    RecyclerView.Adapter<FoodTruckReviewsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentFoodTruckReviewsListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentFoodTruckReviewsListItemBinding.inflate(LayoutInflater.from(parent.context),
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

    fun updateItems(reviews: List<FoodTruckReviews>) {
        this.reviews = reviews;
        notifyDataSetChanged()
    }

    fun addItem(review: FoodTruckReviews) {
        this.reviews = this.reviews + review;
        notifyDataSetChanged()
    }
}
