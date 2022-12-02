package com.restaurant.ui.viewModel

import androidx.lifecycle.*
import com.restaurant.core.base.BaseViewModel
import com.restaurant.core.dto.Restaurant
import com.restaurant.core.dto.SortType
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.core.utils.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RestaurantViewModel(
    private val repository: RestaurantRepository
) : BaseViewModel() {

    private val _error: SingleLiveEvent<Throwable> = SingleLiveEvent()
    private val _loading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _list: MutableLiveData<List<Restaurant>> = MutableLiveData()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        loadRestaurants()
    }

    fun loadRestaurants(query : String = "", sort : SortType = SortType.Undefine) {
        viewModelScope.launch {
            _loading.postValue(true)
            repository.restaurantList(query, sort)
                .catch {
                    _error.postValue(it)
                }
                .collectLatest {
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


class RestaurantViewModelFactory(
    private val repository: RestaurantRepository
) : AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        return RestaurantViewModel(repository) as T
    }

}