package com.alexanderhoang.foodtruck

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class TabStateAdapter(fragment: Fragment, foodTruck: FoodTruck) : FragmentStateAdapter(fragment) {
    private val foodTruck: FoodTruck = foodTruck;
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            val fragment = FoodTruckMenuFragment();
            fragment.arguments = Bundle().apply {
                putParcelable("foodTruck", foodTruck);
            };

            fragment
        } else {
            val fragment = FoodTruckReviewsFragment();
            fragment.arguments = Bundle().apply {
                putParcelable("foodTruck", foodTruck);
            };
            fragment;
        }
    }
}