package com.aml.pagging3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aml.pagging3.R
import com.aml.pagging3.model.Product


class ProductAdapter :
    PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false) as TextView
        return ProductViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.textView.text = item?.index.toString()
    }

    class ProductViewHolder(
        val textView: TextView
    ) : ViewHolder(textView)


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.index == newItem.index

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}