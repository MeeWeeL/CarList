package com.meeweel.carlist.ui.fragmentcarlist

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.meeweel.carlist.R
import com.meeweel.carlist.databinding.CarListRecyclerFilterBinding
import com.meeweel.carlist.databinding.FragmentFullscreenPhotoBinding
import com.meeweel.carlist.databinding.FragmentMainRecyclerBinding
import com.meeweel.carlist.domain.CarBrand
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
        binding.toolBar.menu.apply {
            findItem(R.id.filter).setOnMenuItemClickListener {
                showZoomedCarDialog()
                return@setOnMenuItemClickListener true
            }
            findItem(R.id.sort).setOnMenuItemClickListener {
                showPopupMenu(binding.toolBar)
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun setZoomListener() {
        adapter.setZoomListener(object : OnImageZoomListener {
            override fun onImageClick(imageUrl: String) {
                showZoomedCarDialog(imageUrl)
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

    private fun showZoomedCarDialog(imageUrl: String) {
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
            data.error.message?.let { toast(it) }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END)
        popupMenu.inflate(R.menu.sort_menu)
        popupMenu.setForceShowIcon(true)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.by_brand -> {
                    toast("by brand")
                    return@setOnMenuItemClickListener true
                }
                R.id.by_model -> {
                    toast("by model")
                    return@setOnMenuItemClickListener true
                }
                R.id.by_cost_down -> {
                    toast("by cost down")
                    return@setOnMenuItemClickListener true
                }
                R.id.by_cost_up -> {
                    toast("by cost up")
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    private fun showZoomedCarDialog() {
        val dialog = Dialog(requireContext())
        val filterBinding = CarListRecyclerFilterBinding.inflate(layoutInflater)
        dialog.setContentView(filterBinding.root)
        val brands = CarBrand.values()
        filterBinding.brandSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, brands)
        dialog.show()

        filterBinding.filterOkBtn.setOnClickListener {
            toast(brands[filterBinding.brandSpinner.selectedItemPosition].brand)
            dialog.cancel()
        }
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
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