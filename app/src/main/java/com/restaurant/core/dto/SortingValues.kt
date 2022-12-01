package com.restaurant.core.dto

import androidx.annotation.Keep


@Keep
data class SortingValues(
    val bestMatch : Double,
    val newest : Double,
    val ratingAverage : Double,
    val distance : Long,
    val popularity : Double,
    val averageProductPrice : Long,
    val deliveryCosts : Long,
    val minCost : Long
)