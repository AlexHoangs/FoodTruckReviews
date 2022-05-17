package com.alexanderhoang.foodtruck

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexanderhoang.foodtruck.databinding.FragmentFoodTruckReviewsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoodTruckReviewsFragment : Fragment() {
    private val args: FoodTruckReviewsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getAndSetReviews(recyclerViewAdapter: FoodTruckReviewsRecyclerViewAdapter) {
        MainActivity.foodTruckService.listFoodTruckReviews(args.foodTruck.id)
            .enqueue(object : Callback<List<FoodTruckReviews>> {
                override fun onResponse(
                    call: Call<List<FoodTruckReviews>>,
                    response: Response<List<FoodTruckReviews>>
                ) {
                    recyclerViewAdapter.updateItems(response.body()!!);
                }
                override fun onFailure(call: Call<List<FoodTruckReviews>>, t: Throwable) {

                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodTruckReviewsBinding.inflate(inflater, container, false);
        val view = inflater.inflate(R.layout.fragment_food_truck_reviews, container, false)
        val recyclerViewAdapter = FoodTruckReviewsRecyclerViewAdapter(listOf());

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), MainActivity.gso);

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data;

                    val account = GoogleSignIn.getSignedInAccountFromIntent(data).result;

                    binding.signinButton.visibility = View.GONE;
                    binding.reviewInput.visibility = View.VISIBLE;
                }
        }

        binding.signinButton.setOnClickListener {
            resultLauncher.launch(mGoogleSignInClient.signInIntent);
        };

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity());

        if (account != null) {
            binding.signinButton.visibility = View.GONE;
            binding.reviewInput.visibility = View.VISIBLE;
        }

        binding.submitButton.setOnClickListener {
            val account = GoogleSignIn.getLastSignedInAccount(requireActivity());

            binding.submitButton.isEnabled = false;
            MainActivity.foodTruckService.createFoodTruckReview("Bearer " + account!!.idToken,
            args.foodTruck.id, FoodTruckReviewPayload(binding.reviewText.text.toString(), listOf())
            ).enqueue(object: Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    binding.submitButton.isEnabled = true;
                    getAndSetReviews(recyclerViewAdapter);
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    binding.submitButton.isEnabled = true;
                    Log.d("Warning", "Failed to send POST to reviews")
                }
            })
            binding.reviewText.text.clear();
            binding.reviewText.clearFocus();
        }

        binding.recycler.apply {
            adapter = recyclerViewAdapter;
            layoutManager = LinearLayoutManager(context)
        }

        getAndSetReviews(recyclerViewAdapter);

        return binding.root;
    }

}