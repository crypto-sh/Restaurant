package com.restaurant.ui.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.restaurant.core.base.BaseViewModel
import com.restaurant.core.dto.Restaurant
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.core.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class RestaurantViewModel(
    private val repository: RestaurantRepository
) : BaseViewModel() {

    private val _error: SingleLiveEvent<Throwable> = SingleLiveEvent()
    private val _loading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _list: MutableLiveData<List<Restaurant>> = MutableLiveData()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        _loading.postValue(true)
        viewModelScope.launch {
            repository.restaurantList()
                .collect {
                    _loading.postValue(false)
                    if (it.isSuccess) {
                        _list.postValue(it.getOrNull())
                    } else {
                        _error.postValue(it.exceptionOrNull())
                    }
                }
        }
    }

    fun observableLoading(): LiveData<Boolean> = _loading

    fun observableRestaurants(): LiveData<List<Restaurant>> = _list

    fun observableError(): LiveData<Throwable> = _error
}