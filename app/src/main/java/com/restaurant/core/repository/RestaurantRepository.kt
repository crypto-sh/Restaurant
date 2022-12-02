package com.restaurant.core.repository

import com.restaurant.core.dto.Restaurant
import com.restaurant.core.dto.SortType
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun restaurantList(query : String = "", sort: SortType = SortType.Undefine): Flow<Result<List<Restaurant>>>

    suspend fun savedSortType(sort: SortType = SortType.Undefine) : SortType
}
