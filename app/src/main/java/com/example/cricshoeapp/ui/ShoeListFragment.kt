package com.example.cricshoeapp.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cricshoeapp.R
import com.example.cricshoeapp.ShoeApplication
import com.example.cricshoeapp.databinding.ActivityMainBinding
import com.example.cricshoeapp.databinding.FragmentShoeListBinding
import com.example.cricshoeapp.viewmodel.MainViewModel
import com.example.cricshoeapp.viewmodel.ShoeListViewModel
import javax.inject.Inject

class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.create(
            ShoeListViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shoe_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as ShoeApplication).appComponent.inject(this)
    }
}