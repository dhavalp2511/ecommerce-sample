package com.example.ecommercedemo.ui.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.ecommercedemo.R
import com.example.ecommercedemo.common.UiState
import com.example.ecommercedemo.databinding.FragmentProductListBinding
import com.example.ecommercedemo.ui.main.MainFragmentDirections
import com.example.ecommercedemo.ui.productlist.adapter.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val productListViewModel: ProductListViewModel by viewModels()
    val adapter by lazy {
        ProductListAdapter {
            it.product?.let { product ->
                parentFragment?.activity?.findNavController(R.id.navHostFragment)
                    ?.navigate(MainFragmentDirections.actionMainFragmentToProductDetailsFragment(product))
            }

        }
    }
    lateinit var binding: FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductListBinding.inflate(inflater, container, false)
        binding.viewModel = productListViewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (productListViewModel.productsLiveData.value == null) {
            productListViewModel.getProducts()
        }

        binding.rvProducts.adapter = adapter
        binding.toolbar.toolbar.title = getString(R.string.products)
        viewModelSetup()
    }

    private fun viewModelSetup() {
        productListViewModel.productsLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is UiState.Error -> {
                        progressbar.isVisible = false
                        Toast.makeText(requireContext(),it.throwable.message,Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Loading -> {
                        progressbar.isVisible = true
                    }
                    is UiState.Success ->             {
                        progressbar.isVisible = false
                        adapter.setData(it.data)
                    }
                }
            }
        }
    }
}