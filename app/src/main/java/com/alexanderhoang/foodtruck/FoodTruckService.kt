package com.alexanderhoang.foodtruck

import retrofit2.Call
import retrofit2.http.*

interface FoodTruckService {
    @GET("food-trucks")
    fun listFoodTrucks(): Call<List<FoodTruck>>

    @GET("food-trucks/{id}/items")
    fun listFoodItems(@Path("id") truckId: String): Call<List<FoodItem>>

    @GET("food-trucks/{truckId}/reviews")
    fun listFoodTruckReviews(@Path("truckId") truckId: String): Call<List<FoodTruckReviews>>

    @POST("food-trucks/{truckId}/reviews")
    @Headers("Content-type: application/json")
    fun createFoodTruckReview(@Header("Authorization") authorization: String,
                              @Path("truckId") truckId: String,
                              @Body payload: FoodTruckReviewPayload): Call<Void>
}