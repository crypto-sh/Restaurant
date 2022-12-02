package com.restaurant.core.repository

import com.restaurant.core.dto.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun restaurantList(query : String = ""): Flow<Result<List<Restaurant>>>
}
