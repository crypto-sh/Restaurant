package com.restaurant.core.dto

import androidx.annotation.Keep


@Keep
data class Restaurant(
    val name: String,
    val status: String,
    val sortingValues: SortingValues
)