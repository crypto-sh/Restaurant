package com.restaurant.ui

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.restaurant.R
import com.restaurant.core.base.BaseActivity
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.core.utils.FileHelper
import com.restaurant.core.utils.showMessage
import com.restaurant.databinding.ActivityRestaurantBinding
import com.restaurant.ui.adapter.RvAdapterRestaurant
import com.restaurant.ui.viewModel.RestaurantViewModel

class RestaurantActivity : BaseActivity<ActivityRestaurantBinding, RestaurantViewModel>() {

    private val adapter : RvAdapterRestaurant by lazy {
        RvAdapterRestaurant()
    }

    override fun getResourceLayoutId(): Int = R.layout.activity_restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.restaurantRecyclerView.adapter = adapter
        viewModel.observableLoading().observe(this){
            binding.loading = it == true
        }
        viewModel.observableError().observe(this){
            val msg = it.message ?: getString(R.string.general_error)
            binding.showMessage(msg)
        }
        viewModel.observableRestaurants().observe(this) {
            adapter.submitList(it)
        }
    }

    override fun getViewModelClass(): Class<RestaurantViewModel> = RestaurantViewModel::class.java

    override fun getFactory(): ViewModelProvider.Factory {
        return object : AbstractSavedStateViewModelFactory() {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                @Suppress("UNCHECKED_CAST")
                return RestaurantViewModel(RestaurantRepository(FileHelper(application))) as T
            }
        }
    }
}