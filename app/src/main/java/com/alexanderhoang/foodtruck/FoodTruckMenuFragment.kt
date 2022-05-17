package com.alexanderhoang.foodtruck

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexanderhoang.foodtruck.databinding.FragmentFoodTruckMenuBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodTruckMenuFragment : Fragment() {
    private val args: FoodTruckMenuFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodTruckMenuBinding.inflate(inflater, container, false)
        val recyclerViewAdapter = FoodItemListRecyclerViewAdapter(listOf())

        args.foodTruck.let {
            binding.apply {
                foodItemListRecyclerView.apply {
                    adapter = recyclerViewAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }

            (requireActivity() as MainActivity).apply {
                title = it.name

                MainActivity.foodTruckService.listFoodItems(it.id).enqueue(object : Callback<List<FoodItem>> {
                    override fun onResponse(
                        call: Call<List<FoodItem>>,
                        response: Response<List<FoodItem>>) {
                        recyclerViewAdapter.updateItems(response.body()!!);
                        MainActivity.db?.foodItemDao()?.insertFoodItems(response.body()!!);
                    }

                    override fun onFailure(call: Call<List<FoodItem>>, t: Throwable) {
                        recyclerViewAdapter.updateItems(
                            MainActivity.db?.foodItemDao()?.getAllTruckItems(args.foodTruck.id)!!);
                    }
                })
            }
        }

        return binding.root
    }
}