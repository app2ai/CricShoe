package com.example.cricshoeapp.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cricshoeapp.databinding.FragmentShoeListBinding
import com.example.cricshoeapp.model.Sneaker

class ShoeAdapter : Adapter<ShoeAdapter.ShoeViewHolder>() {

    private lateinit var binding: FragmentShoeListBinding

    inner class ShoeViewHolder : ViewHolder(binding.root) {

    }

    fun setupShoeData(list: List<Sneaker>) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ShoeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    private val differCallback = object : DiffUtil.ItemCallback<Sneaker>() {
        override fun areItemsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}