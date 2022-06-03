package com.meeweel.carlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.carlist.databinding.ItemMainRecyclerBinding
import com.meeweel.carlist.domain.CarModel

class CarListFragmentAdapter :
    ListAdapter<CarModel, CarListFragmentAdapter.CarListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListViewHolder {
        return CarListViewHolder(
            ItemMainRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CarListViewHolder(private val itemBinding: ItemMainRecyclerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(carItem: CarModel) {
            itemBinding.apply {
                brand.text = carItem.brand.brand
                model.text = carItem.model
                cost.text = carItem.cost.toString()
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CarModel>() {

        override fun areItemsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}