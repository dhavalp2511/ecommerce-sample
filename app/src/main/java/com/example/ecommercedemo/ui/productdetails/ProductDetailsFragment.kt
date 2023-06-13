package com.example.ecommercedemo.ui.productdetails

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ecommercedemo.R
import com.example.ecommercedemo.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private val args by navArgs<ProductDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.toolbar.title = args.product.name
        binding.toolbar.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_24)
        binding.toolbar.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.apply {
            productName.text = args.product.name
            productDescription.text = args.product.productDesc
            Glide.with(binding.root).load(args.product.productUrl).centerCrop().into(productImage)
            discountedProductPrice.text = binding.root.context.getString(
                R.string.product_price,
                args.product.offerPrice
            )
            productPrice.text = binding.root.context.getString(
                R.string.product_price,
                args.product.price
            )
            productPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
    }
}