package com.meeweel.carlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.meeweel.carlist.databinding.FragmentMainRecyclerBinding
import com.meeweel.carlist.domain.CarListState

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
        setRecyclerView()
        setObserver()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}