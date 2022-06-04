package com.meeweel.carlist.ui.fragmentcarlist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.meeweel.carlist.R
import com.meeweel.carlist.databinding.FragmentFullscreenPhotoBinding
import com.meeweel.carlist.databinding.FragmentMainRecyclerBinding
import com.meeweel.carlist.domain.CarListState
import com.meeweel.carlist.ui.fragmentcardetails.CarDetailsFragment.Companion.ARG_CAR_ID


class CarListFragment : Fragment() {

    private var _binding: FragmentMainRecyclerBinding? = null
    private val binding: FragmentMainRecyclerBinding
        get() = _binding!!

    private val adapter = CarListFragmentAdapter()
    private val viewModel: CarListViewModel by lazy {
        ViewModelProvider(this).get(CarListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setZoomListener()
        setRecyclerView()
        setObserver()
    }

    private fun setZoomListener() {
        adapter.setZoomListener(object : OnImageZoomListener {
            override fun onImageClick(imageUrl: String) {
                showFilterDialog(imageUrl)
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
    }

    private fun showFilterDialog(imageUrl: String) {
        val dialog = Dialog(requireContext())
        val zoomBinding = FragmentFullscreenPhotoBinding.inflate(layoutInflater)
        dialog.setContentView(zoomBinding.root)
        dialog.show()
        dialog.window?.attributes = getWindowParams(dialog)
        setImage(zoomBinding.zoomedImage, imageUrl)
    }

    private fun getWindowParams(dialog: Dialog): WindowManager.LayoutParams {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        return lp
    }

    private fun setImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .error(R.drawable.ic_no_connection)
            .placeholder(R.drawable.ic_car)
            .into(imageView)
    }

    private fun setObserver() {
        val observer = Observer<CarListState> { data ->
            renderData(data)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getCarList()
    }

    private fun setRecyclerView() {
        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
    }

    private fun renderData(data: CarListState) = when (data) {
        is CarListState.Success -> {
            val carList = data.carList
            adapter.submitList(carList)
            binding.loadingLayout.visibility = View.GONE
        }
        is CarListState.Loading -> {
            binding.loadingLayout.visibility = View.VISIBLE
        }
        is CarListState.Error -> {
            binding.loadingLayout.visibility = View.GONE
            Toast.makeText(requireContext(), data.error.message, Toast.LENGTH_SHORT).show()
        }
    }

    interface OnImageZoomListener {
        fun onImageClick(imageUrl: String)
    }

    interface OnItemListener {
        fun onItemClick(carId: Int)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        adapter.removeZoomListener()
    }
}