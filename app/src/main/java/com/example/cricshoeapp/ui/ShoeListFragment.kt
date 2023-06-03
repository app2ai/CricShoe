package com.example.cricshoeapp.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.cricshoeapp.ShoeApplication
import com.example.cricshoeapp.databinding.FragmentShoeListBinding
import com.example.cricshoeapp.viewmodel.Failed
import com.example.cricshoeapp.viewmodel.InProgress
import com.example.cricshoeapp.viewmodel.ShoeListViewModel
import com.example.cricshoeapp.viewmodel.Success
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private val adapter: ShoeAdapter by lazy {
        ShoeAdapter()
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.create(
            ShoeListViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentShoeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchAllShoeFromDb()

        with(binding.shoeRecyclerView){
            layoutManager = GridLayoutManager(requireContext(), 2, VERTICAL, false)
            adapter = adapter
        }

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch{
            viewModel.sneakerData.collect{
                when(it) {
                    InProgress -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Failed -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Oops, unable to load data. Please clear data and Retry", Toast.LENGTH_LONG).show()
                    }
                    is Success -> {
                        binding.progressBar.visibility = View.GONE
                        // Set data to recycler
                        Toast.makeText(context, "${it.shoes.size} Data Loaded", Toast.LENGTH_SHORT).show()
                        adapter.differ.submitList(it.shoes)
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as ShoeApplication).appComponent.inject(this)
    }
}