package com.meeweel.carlist.ui.fragmentcarlist

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meeweel.carlist.R
import com.meeweel.carlist.databinding.BottomSheetCarChangingBinding
import com.meeweel.carlist.databinding.CarListRecyclerFilterBinding
import com.meeweel.carlist.databinding.FragmentFullscreenPhotoBinding
import com.meeweel.carlist.databinding.FragmentMainRecyclerBinding
import com.meeweel.core.domain.CarBrand
import com.meeweel.core.domain.CarColor
import com.meeweel.core.domain.CarModel
import com.meeweel.carlist.util.*

abstract class BaseCarListFragment : Fragment() {

    private var _binding: FragmentMainRecyclerBinding? = null
    internal val binding: FragmentMainRecyclerBinding
        get() = _binding!!

    abstract val viewModel: CarListViewModel

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
        setAdapterListenersListener()
        setRecyclerView()
        setObserver()
        setToolBar()
        setFAB()
    }

    abstract fun setObserver()

    abstract fun setRecyclerView()

    abstract fun setAdapterListenersListener()

    private fun setToolBar() {
        binding.toolBar.menu.apply {
            findItem(R.id.filter).setOnMenuItemClickListener {
                showFilterDialog()
                return@setOnMenuItemClickListener true
            }
            findItem(R.id.sort).setOnMenuItemClickListener {
                showPopupMenu(binding.toolBar)
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END)
        popupMenu.inflate(R.menu.sort_menu)
        popupMenu.setForceShowIcon(true)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.by_brand -> viewModel.sort(CarListViewModel.SortBy.BRAND_NAME)
                R.id.by_model -> viewModel.sort(CarListViewModel.SortBy.MODEL)
                R.id.by_cost_down -> viewModel.sort(CarListViewModel.SortBy.COST_DOWN)
                R.id.by_cost_up -> viewModel.sort(CarListViewModel.SortBy.COST_UP)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    internal fun callBottomSheetDialog(car: CarModel? = null) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetCarChangingBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        val brands = CarBrand.values()
        bottomSheetBinding.bottomSheetBrandSpinner.setBrands(requireContext(), brands)
        val colors = CarColor.values()
        bottomSheetBinding.bottomSheetColorSpinner.setColors(requireContext(), colors)
        if (car != null) {
            bottomSheetBinding.bottomSheetCarImage.loadPicture(car.image)
            bottomSheetBinding.apply {
                bottomSheetModel.setText(car.model)
                bottomSheetMileage.setText(car.mileage.toString())
                bottomSheetYear.setText(car.year.toString())
                bottomSheetCost.setText(car.cost.toString())
                bottomSheetAddBtn.text = UPDATE
            }
        } else {
            bottomSheetBinding.bottomSheetCarImage.loadPicture(EXAMPLE_IMAGE_URL)
        }
        bottomSheetBinding.apply {
            bottomSheetAddBtn.setOnClickListener {
                try {
                    val mileage = bottomSheetMileage.text.toString().toInt()
                    val newCarData = CarModel(
                        car?.id ?: 0,
                        brands[bottomSheetBrandSpinner.selectedItemPosition],
                        bottomSheetModel.text.toString(),
                        bottomSheetYear.text.toString().toInt(),
                        car?.image ?: EXAMPLE_IMAGE_URL,
                        mileage == 0,
                        mileage,
                        colors[bottomSheetColorSpinner.selectedItemPosition],
                        bottomSheetCost.text.toString().toInt()
                    )
                    viewModel.addNewCarData(newCarData)
                } catch (e: Exception) {
                    e.message.toString().toast(requireContext())
                } finally {
                    bottomSheetDialog.dismiss()
                }
            }
        }
        bottomSheetDialog.show()
    }

    private fun showFilterDialog() {
        val dialog = Dialog(requireContext())
        val filterBinding = CarListRecyclerFilterBinding.inflate(layoutInflater)
        dialog.setContentView(filterBinding.root)
        val brands = CarBrand.values()
        filterBinding.brandSpinner.adapter =
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                brands
            )
        dialog.show()

        filterBinding.filterOkBtn.setOnClickListener {
            viewModel.filter(brands[filterBinding.brandSpinner.selectedItemPosition])
            brands[filterBinding.brandSpinner.selectedItemPosition].brand.toast(requireContext())
            dialog.cancel()
        }
    }

    internal fun showPhotoDialog(imageUrl: String) {
        val dialog = Dialog(requireContext())
        val zoomBinding = FragmentFullscreenPhotoBinding.inflate(layoutInflater)
        dialog.setContentView(zoomBinding.root)
        dialog.show()
        dialog.window?.attributes = getWindowParams(dialog)
        zoomBinding.zoomedImage.loadPicture(imageUrl)
    }

    private fun setFAB() {
        binding.fab.setOnClickListener {
            callBottomSheetDialog()
        }
    }

    private fun getWindowParams(dialog: Dialog): WindowManager.LayoutParams {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        return lp
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val UPDATE = "Update"
        const val EXAMPLE_IMAGE_URL =
            "https://i.pinimg.com/originals/a0/20/66/a020669bafe5cd139d3958ff3563c23c.jpg"
    }
}
