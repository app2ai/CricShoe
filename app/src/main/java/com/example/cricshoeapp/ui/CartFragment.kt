package com.example.cricshoeapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cricshoeapp.R
import com.example.cricshoeapp.ShoeApplication
import com.example.cricshoeapp.databinding.FragmentCartBinding
import com.example.cricshoeapp.databinding.FragmentShoeListBinding
import com.example.cricshoeapp.viewmodel.ShoeListViewModel
import javax.inject.Inject

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.create(
            ShoeListViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as ShoeApplication).appComponent.inject(this)
    }
}