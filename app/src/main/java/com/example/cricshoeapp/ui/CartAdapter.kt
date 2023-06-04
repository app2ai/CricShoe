package com.example.cricshoeapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cricshoeapp.R
import com.example.cricshoeapp.databinding.CartListItemBinding
import com.example.cricshoeapp.model.Sneaker
import com.example.cricshoeapp.utils.ShoeCartItemListener
import com.squareup.picasso.Picasso

class CartAdapter(listener: ShoeCartItemListener) : Adapter<CartAdapter.CartViewHolder>() {

    lateinit var binding: CartListItemBinding
    private var mlistener: ShoeCartItemListener = listener

    inner class CartViewHolder : ViewHolder(binding.root) {
        fun setData(item: Sneaker) {
            binding.apply {
                txtShoeName.text = item.name
                // $ 100
                txtShoePrice.text = this.root.context.getString(
                    R.string.cart_shoe_price,
                    item.retail_price_cents / 100
                )
                btnRemoveFromCart.setOnClickListener {
                    mlistener.clickToRemoveItem(item.id)
                }
            }
            Picasso.get()
                .load(item.main_picture_url)
                .fit()
                .placeholder(R.drawable.baseline_downloading)
                .into(binding.imgCartSneaker)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        binding = CartListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder()
    }

    override fun getItemCount() = differCart.currentList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.setData(differCart.currentList[position])
        holder.setIsRecyclable(true)
    }

    private val differCallbackCart = object : DiffUtil.ItemCallback<Sneaker>() {
        override fun areItemsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem == newItem
        }
    }

    val differCart = AsyncListDiffer(this, differCallbackCart)
}