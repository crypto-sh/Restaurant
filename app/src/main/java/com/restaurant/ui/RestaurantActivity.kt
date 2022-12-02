package com.restaurant.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.restaurant.R
import com.restaurant.core.base.BaseActivity
import com.restaurant.core.dto.SortType
import com.restaurant.core.utils.handleSearchView
import com.restaurant.core.utils.showMessage
import com.restaurant.databinding.ActivityRestaurantBinding
import com.restaurant.ui.adapter.RvAdapterRestaurant
import com.restaurant.ui.viewModel.RestaurantViewModel
import com.restaurant.ui.viewModel.RestaurantViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RestaurantActivity : BaseActivity<ActivityRestaurantBinding, RestaurantViewModel>() {

    @Inject
    lateinit var factory: RestaurantViewModelFactory

    private val adapter: RvAdapterRestaurant by lazy {
        RvAdapterRestaurant()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.restaurantRecyclerView.adapter = adapter
        viewModel.observableLoading().observe(this) {
            binding.loading = it == true
        }
        viewModel.observableError().observe(this) {
            val msg = it.message ?: getString(R.string.general_error)
            binding.showMessage(msg)
        }
        viewModel.observableRestaurants().observe(this) {
            adapter.submitList(it)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.restaurant_menu, menu)
        menu.findItem(R.id.menu_search).handleSearchView {
            viewModel.loadRestaurants(it)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.popup_menu_open -> {
                viewModel.loadRestaurants(sort = SortType.OpenStatus)
            }
            R.id.popup_menu_popular -> {
                viewModel.loadRestaurants(sort = SortType.Popularity)
            }
            R.id.popup_menu_distance -> {
                viewModel.loadRestaurants(sort = SortType.Distance)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getFactory(): ViewModelProvider.Factory = factory

    override fun getResourceLayoutId(): Int = R.layout.activity_restaurant

    override fun getViewModelClass(): Class<RestaurantViewModel> = RestaurantViewModel::class.java
}