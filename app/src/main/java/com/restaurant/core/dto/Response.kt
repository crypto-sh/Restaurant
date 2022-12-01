package com.restaurant.core.dto

import androidx.annotation.Keep


@Keep
data class RestaurantResponse(
    val restaurants : List<Restaurant>
)