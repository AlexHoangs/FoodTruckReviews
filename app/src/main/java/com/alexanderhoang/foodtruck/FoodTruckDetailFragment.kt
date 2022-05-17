package com.alexanderhoang.foodtruck

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.alexanderhoang.foodtruck.databinding.FragmentFoodTruckDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoodTruckDetailFragment : Fragment() {
    private val args:FoodTruckDetailFragmentArgs by navArgs()
    private var _binding: FragmentFoodTruckDetailBinding?= null
    private val foodTruckReviews = listOf<FoodTruckReviews>()
    private val binding get() = _binding!!

    private lateinit var tabStateAdapter: TabStateAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        tabStateAdapter = TabStateAdapter(this, args.foodTruck);
        viewPager.adapter = tabStateAdapter;

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (if (position == 0) "Menu" else "Reviews");
        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val service = MainActivity.foodTruckService;

        _binding = FragmentFoodTruckDetailBinding.inflate(inflater, container, false)
        val recyclerViewAdapter = FoodItemListRecyclerViewAdapter(listOf())

        args.foodTruck.let {
            binding.apply {
                Glide.with(root).load(it.imageUrl).into(foodTruckDetailImage)
                foodTruckDetailPriceLevel.text = "$".repeat(it.priceLevel)
                foodTruckDetailLocation.text = it.location
                foodTruckDetailTime.text = it.formattedTimeInterval
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
                        recyclerViewAdapter.updateItems(MainActivity.db?.foodItemDao()?.getAllTruckItems(args.foodTruck.id)!!)
                    }
                })
            }
        }

        return binding.root
    }

}