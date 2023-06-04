package com.example.cricshoeapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cricshoeapp.R
import com.example.cricshoeapp.databinding.FragmentShoeListBinding
import com.example.cricshoeapp.databinding.ListShoeCardBinding
import com.example.cricshoeapp.model.Sneaker
import com.example.cricshoeapp.utils.ShoeItemListener
import com.squareup.picasso.Picasso

class ShoeAdapter(private val listener: ShoeItemListener) : Adapter<ShoeAdapter.ShoeViewHolder>() {

    private lateinit var binding: ListShoeCardBinding
    private var slistener: ShoeItemListener = listener

    inner class ShoeViewHolder : ViewHolder(binding.root) {
        fun setData(item : Sneaker){
            binding.apply {
                txtSneakPrice.text = "\$${item.retail_price_cents/100}"
                txtShoeName.text = item.name
                fabAddToCart.setOnClickListener {
                    slistener.clickToAddInCart(item.id)
                }
                root.setOnClickListener {
                    slistener.clickToGoDetailPage(item.id)
                }
            }
            Picasso.get()
                .load(item.grid_picture_url)
                .fit()
                .placeholder(R.drawable.baseline_downloading)
                .into(binding.imgSneaker)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeViewHolder {
        binding = ListShoeCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShoeViewHolder()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ShoeViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(true)
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