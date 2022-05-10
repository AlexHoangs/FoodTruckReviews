package com.ecs198f.foodtrucks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ecs198f.foodtrucks.databinding.FragmentFoodTruckReviewsBinding
import retrofit2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoodTruckReviewsFragment : Fragment() {

    private val args:FoodTruckReviewsFragmentArgs by navArgs()
    private var _binding:FragmentFoodTruckReviewsBinding ?= null
    private val foodTruckReviews = listOf<FoodTruckReviews>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val service = (requireActivity() as MainActivity).foodTruckService

        _binding = FragmentFoodTruckReviewsBinding.inflate(inflater, container, false)

        val foodTruckReview = args.foodTruckReviews
        binding.foodTruckReviewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FoodTruckReviewsRecyclerViewAdapter(foodTruckReviews)
        }


        service.listFoodTruckReviews(foodTruckReview.id).enqueue(object : Callback<List<FoodTruckReviews>> {
            override fun onResponse(
                call: Call<List<FoodTruckReviews>>,
                response: Response<List<FoodTruckReviews>>
            ) {
                binding.foodTruckReviewsRecyclerView.adapter = FoodTruckReviewsRecyclerViewAdapter(response.body()!!)
            }

            override fun onFailure(call: Call<List<FoodTruckReviews>>, t: Throwable) {
                throw t
            }
        })


        return binding.root
    }

}