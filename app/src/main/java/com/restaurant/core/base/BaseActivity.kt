package com.restaurant.core.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<E : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    lateinit var binding : E

    lateinit var viewModel : V

    abstract fun getResourceLayoutId() : Int

    abstract fun getViewModelClass(): Class<V>

    abstract fun getFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getResourceLayoutId())
        viewModel = ViewModelProvider(this, getFactory())[getViewModelClass()]
        lifecycle.addObserver(viewModel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}