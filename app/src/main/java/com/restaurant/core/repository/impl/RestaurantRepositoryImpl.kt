package com.restaurant.core.repository.impl

import com.google.gson.Gson
import com.restaurant.core.dto.Restaurant
import com.restaurant.core.dto.RestaurantResponse
import com.restaurant.core.dto.SortType
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.core.utils.FileHelper
import com.restaurant.core.utils.PreferenceHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RestaurantRepositoryImpl(
    private val fileHelper: FileHelper,
    private val preferenceHandler: PreferenceHandler
) : RestaurantRepository {

    override suspend fun restaurantList(
        query: String,
        sort: SortType
    ): Flow<Result<List<Restaurant>>> {
        return withContext(Dispatchers.Default) {
            flow {
                val json = fileHelper.load("example.json")
                val restaurants = Gson().fromJson(json, RestaurantResponse::class.java).restaurants
                val finalList = when (savedSortType(sort)) {
                    SortType.OpenStatus -> {
                        restaurants.sortedBy {
                            if (it.status == "open") 0 else 1
                        }
                    }
                    SortType.Popularity -> {
                        restaurants.sortedByDescending {
                            it.sortingValues.popularity
                        }
                    }
                    SortType.Distance -> {
                        restaurants.sortedBy {
                            it.sortingValues.distance
                        }
                    }
                    else -> restaurants
                }.filter { it.name.contains(query) }
                emit(Result.success(finalList))
            }.catch {
                emit(Result.failure(it))
            }
        }
    }

    override suspend fun savedSortType(sort: SortType): SortType {
        val key = "bundle_sort_type"
        val value = preferenceHandler.getString(key)
        return if (sort != SortType.Undefine) {
            preferenceHandler.put(key, sort.value)
            sort
        } else {
            SortType[value]
        }
    }
}