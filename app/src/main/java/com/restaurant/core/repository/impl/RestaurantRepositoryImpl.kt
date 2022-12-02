package com.restaurant.core.repository.impl

import com.google.gson.Gson
import com.restaurant.core.dto.Restaurant
import com.restaurant.core.dto.RestaurantResponse
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.core.utils.FileHelper
import com.restaurant.core.utils.PreferenceHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext



class RestaurantRepositoryImpl(
    private val fileHelper: FileHelper
) : RestaurantRepository {

    override suspend fun restaurantList(query : String): Flow<Result<List<Restaurant>>> {
        return withContext(Dispatchers.Default) {
            flow {
                val json = fileHelper.load("example.json")
                val result = Gson().fromJson(json, RestaurantResponse::class.java)
                emit(Result.success(result.restaurants.filter { it.name.contains(query) }))
            }.catch {
                emit(Result.failure(it))
            }
        }
    }
}