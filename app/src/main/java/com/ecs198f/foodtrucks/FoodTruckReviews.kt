package com.ecs198f.foodtrucks

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodTruckReviews (
    val id: String,
    val truckId: String,
    val authorName: String,
    val authorAvatarUrl: String,
    val content: String,
    val imageUrls: String
): Parcelable {}