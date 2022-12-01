package com.restaurant.base

import com.restaurant.core.dto.Restaurant
import com.restaurant.core.dto.SortingValues


fun createRestaurant(name: String = "Mama Mia", status: String = "order ahead") = Restaurant(
    name,
    status,
    SortingValues(
        7.0,
        250.0,
        4.0,
        1396,
        6.0,
        912,
        0,
        1000
    )
)

fun createRestaurants(count: Int): List<Restaurant> {
    val restaurants = mutableListOf<Restaurant>()
    for (index in 0 until count) {
        restaurants.add(createRestaurant("index $index"))
    }
    return restaurants
}