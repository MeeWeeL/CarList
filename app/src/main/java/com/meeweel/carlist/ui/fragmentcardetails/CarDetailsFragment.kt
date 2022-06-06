package com.meeweel.carlist.ui.fragmentcardetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.meeweel.carlist.databinding.FragmentCarDetailsBinding
import com.meeweel.core.domain.CarModel
import com.meeweel.carlist.util.loadPicture

class CarDetailsFragment : Fragment() {

    private var _binding: FragmentCarDetailsBinding? = null
    private val binding: FragmentCarDetailsBinding
        get() = _binding!!

    private val viewModel: CarDetailsViewModel by lazy {
        ViewModelProvider(this).get(CarDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carId = requireArguments().getInt(ARG_CAR_ID)
        setObserver(carId)
    }

    private fun setObserver(carId: Int) {
        val observer = Observer<CarModel> { data ->
            renderData(data)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getCar(carId)
    }

    private fun renderData(data: CarModel) {
        binding.detailsImage.loadPicture(data.image)
        binding.apply {
            detailsBrand.text = data.brand.brand
            detailsModel.text = data.model
            detailsColor.text = data.color.color
            detailsIsNew.text = if (data.isNew) NEW else USED
            ("${data.mileage} km").also { detailsMileage.text = it }
            ("${data.cost} RUB").also { detailsCost.text = it }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_CAR_ID = "Car ID"
        const val NEW = "New"
        const val USED = "Used"
    }
}