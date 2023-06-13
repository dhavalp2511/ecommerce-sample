package com.example.ecommercedemo.ui.productlist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommercedemo.R
import com.example.ecommercedemo.databinding.ItemAdapterProductBinding
import com.example.ecommercedemo.databinding.ItemAdapterRewardsBinding


class ProductListAdapter constructor(private val items : MutableList<ProductListModel> = mutableListOf(),val itemClick : (item : ProductListModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_adapter_product ->
                ProductViewHolder(
                    ItemAdapterProductBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            R.layout.item_adapter_rewards -> RewardViewHolder(
                ItemAdapterRewardsBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_adapter_product -> (holder as ProductViewHolder).bind(position)
            R.layout.item_adapter_rewards -> (holder as RewardViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            ProductListType.PRODUCT -> R.layout.item_adapter_product
            ProductListType.REWARDS -> R.layout.item_adapter_rewards
        }
    }

    fun setData(data: List<ProductListModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(val binding: ItemAdapterProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]
            binding.productLabel.text = item.product?.name
            binding.productDescription.text = item.product?.productDesc
            binding.discountedProductPrice.text =  binding.root.context.getString( R.string.product_price ,item.product?.offerPrice)
            binding.productPrice.text =  binding.root.context.getString( R.string.product_price ,item.product?.price)
            binding.productPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            Glide.with(binding.root).load(item.product?.productUrl).centerCrop().into(binding.productImage)
            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }

    inner class RewardViewHolder(val binding: ItemAdapterRewardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.label.text =  binding.root.context.getString(R.string.click_here_to_join_us_to_get_more_rewards)
            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context,binding.root.context.getString(R.string.thank_you_for_joining_reward_program) , Toast.LENGTH_LONG).show()
            }
        }
    }
}