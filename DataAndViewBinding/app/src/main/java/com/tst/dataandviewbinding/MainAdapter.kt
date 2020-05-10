package com.tst.dataandviewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tst.dataandviewbinding.databinding.MainListItemBinding

class MainAdapter(private val list: List<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding :MainListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.main_list_item, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.executePendingBindings()
    }

    class ViewHolder(val binding: MainListItemBinding) : RecyclerView.ViewHolder(binding.root)

}