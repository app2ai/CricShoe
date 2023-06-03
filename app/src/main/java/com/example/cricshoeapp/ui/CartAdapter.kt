package com.example.cricshoeapp.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cricshoeapp.databinding.FragmentCartBinding

class CartAdapter : Adapter<CartAdapter.CartViewHolder>(){
    lateinit var binding: FragmentCartBinding
    inner class CartViewHolder: ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}