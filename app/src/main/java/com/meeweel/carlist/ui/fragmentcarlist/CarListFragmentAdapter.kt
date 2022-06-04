package com.meeweel.carlist.ui.fragmentcarlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meeweel.carlist.R
import com.meeweel.carlist.databinding.ItemMainRecyclerBinding
import com.meeweel.carlist.domain.CarModel

class CarListFragmentAdapter :
    ListAdapter<CarModel, CarListFragmentAdapter.CarListViewHolder>(DiffCallback) {

    private var zoomListener: CarListFragment.OnImageZoomListener? = null
    private var itemListener: CarListFragment.OnItemListener? = null

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
                color.text = carItem.color.color
                ("${carItem.year}, ${carItem.mileage} km").also {
                    condition.text = it
                }
                ("${carItem.cost} RUB").also { cost.text = it }
                setPicture(this.image, carItem.image)
                image.setOnClickListener {
                    zoomListener?.onImageClick(carItem.image)
                }
                root.setOnClickListener {
                    itemListener?.onItemClick(carItem.id)
                }
            }
        }

        private fun setPicture(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .error(R.drawable.ic_no_connection)
                .placeholder(R.drawable.ic_car)
                .into(imageView)
        }
    }

    fun setZoomListener(zoomListener: CarListFragment.OnImageZoomListener) {
        this.zoomListener = zoomListener
    }

    fun removeZoomListener() {
        this.zoomListener = null
    }

    fun setDetailsListener(zoomListener: CarListFragment.OnItemListener) {
        this.itemListener = zoomListener
    }

    fun removeDetailsListener() {
        this.itemListener = null
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