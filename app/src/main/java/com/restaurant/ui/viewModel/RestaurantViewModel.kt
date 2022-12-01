package com.restaurant.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.restaurant.core.base.BaseViewModel
import com.restaurant.core.dto.Restaurant

class RestaurantViewModel : BaseViewModel() {


    fun observableRestaurants() : LiveData<List<Restaurant>> =  MutableLiveData()
}