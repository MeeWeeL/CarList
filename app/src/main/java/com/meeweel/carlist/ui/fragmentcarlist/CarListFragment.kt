package com.meeweel.carlist.ui.fragmentcarlist

import android.view.*
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.meeweel.carlist.R
import com.meeweel.carlist.domain.CarListState
import com.meeweel.carlist.domain.CarModel
import com.meeweel.carlist.ui.fragmentcardetails.CarDetailsFragment.Companion.ARG_CAR_ID
import com.meeweel.carlist.ui.fragmentcarlist.adapter.CarListFragmentAdapter
import com.meeweel.carlist.ui.fragmentcarlist.adapter.CarListSwipeHelper
import com.meeweel.carlist.ui.fragmentcarlist.adapter.SwipeControllerActions
import com.meeweel.carlist.util.toast


class CarListFragment : BaseCarListFragment() {

    private val adapter = CarListFragmentAdapter()
    override val viewModel: CarListViewModel by lazy {
        ViewModelProvider(this).get(CarListViewModel::class.java)
    }

    override fun setObserver() {
        val observer = Observer<CarListState> { data ->
            renderData(data)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getCarList()
    }

    override fun setRecyclerView() {
        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
        ItemTouchHelper(CarListSwipeHelper(object : SwipeControllerActions {
            override fun onSwipeLeft(car: CarModel) {
                callBottomSheetDialog(car)
            }
        }, adapter)).attachToRecyclerView(binding.mainRecycler)
    }

    private fun renderData(data: CarListState) = when (data) {
        is CarListState.Success -> {
            val carList = data.carList
            adapter.submitList(carList)
            binding.mainRecycler.smoothScrollToPosition(0)
            binding.loadingLayout.visibility = View.GONE
        }
        is CarListState.Loading -> {
            binding.loadingLayout.visibility = View.VISIBLE
        }
        is CarListState.Error -> {
            binding.loadingLayout.visibility = View.GONE
            data.error.message?.toast(requireContext())
        }
    }

    override fun setAdapterListenersListener() {
        adapter.setZoomListener(object : OnImageZoomListener {
            override fun onImageClick(imageUrl: String) {
                showPhotoDialog(imageUrl)
            }
        })
        adapter.setDetailsListener(object : OnItemListener {
            override fun onItemClick(carId: Int) {
                findNavController().navigate(
                    R.id.action_carListFragment_to_carDetailsFragment, bundleOf(
                        ARG_CAR_ID to carId
                    )
                )
            }
        })
        adapter.setLongClickListener(object : OnItemLongClickListener {
            override fun onItemClick(car: CarModel) {
                callBottomSheetDialog(car)
            }
        })
    }

    interface OnImageZoomListener {
        fun onImageClick(imageUrl: String)
    }

    interface OnItemListener {
        fun onItemClick(carId: Int)
    }

    interface OnItemLongClickListener {
        fun onItemClick(car: CarModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.removeListeners()
    }
}