package com.restaurant.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.core.dto.Restaurant
import com.restaurant.databinding.LayoutItemRowRestaurantBinding

class RvAdapterRestaurant : ListAdapter<Restaurant, RvAdapterRestaurant.Holder>(object : DiffUtil.ItemCallback<Restaurant>(){
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean = newItem.name == oldItem.name

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean = newItem.status == oldItem.status
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutItemRowRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(private val binding: LayoutItemRowRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant){
            binding.data = restaurant
        }
    }
}