package com.example.cricshoeapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.cricshoeapp.R
import com.example.cricshoeapp.ShoeApplication
import com.example.cricshoeapp.databinding.FragmentShoeListBinding
import com.example.cricshoeapp.utils.Constants.GRID_VIEW_SPAN_COUNT
import com.example.cricshoeapp.utils.ShoeItemListener
import com.example.cricshoeapp.viewmodel.Failed
import com.example.cricshoeapp.viewmodel.InProgress
import com.example.cricshoeapp.viewmodel.NotAvailable
import com.example.cricshoeapp.viewmodel.ShoeListViewModel
import com.example.cricshoeapp.viewmodel.Success
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoeListFragment : Fragment(), ShoeItemListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private lateinit var binding: FragmentShoeListBinding
    private val mAdapter: ShoeAdapter by lazy {
        ShoeAdapter(this)
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

        with(binding.shoeRecyclerView) {
            layoutManager =
                GridLayoutManager(requireContext(), GRID_VIEW_SPAN_COUNT, VERTICAL, false)
            adapter = mAdapter
            setHasFixedSize(true)
        }
        binding.fabGoToCart.setOnClickListener {
            findNavController().navigate(R.id.cartFragment)
        }
        binding.searchView.setOnQueryTextListener(this)
        binding.searchView.setOnCloseListener(this)
        binding.searchView.setOnSearchClickListener {
            binding.sneakership.visibility = View.INVISIBLE
        }
        observeData()
    }

    // Clear data once we comeback to list page
    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", true)
        binding.sneakership.visibility = View.VISIBLE
    }

    // Filter list as per sneakers name
    private fun filterSneakers(qString: String) {
        viewModel.filterSneakers(qString)
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sneakerData.collect {
                    when (it) {
                        InProgress -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Failed -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Oops, unable to load data. Please clear data and Retry",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.shoeEmptyList.visibility = View.VISIBLE
                        }
                        is Success -> {
                            binding.progressBar.visibility = View.GONE
                            // Set data to recycler
                            mAdapter.differ.submitList(it.shoes)
                            binding.shoeEmptyList.visibility = View.GONE
                        }
                        is NotAvailable -> {
                            mAdapter.differ.submitList(it.emptyList)
                            binding.shoeEmptyList.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as ShoeApplication).appComponent.inject(this)
    }

    override fun clickToAddInCart(sId: Int) {
        viewModel.addItemToCart(sId)
        Toast.makeText(context, "Item $sId added to cart", Toast.LENGTH_SHORT).show()
    }

    override fun clickToGoDetailPage(sId: Int) {
        findNavController().navigate(R.id.detailsFragment, bundleOf("shoeId" to sId))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    // Type then this search will work
    override fun onQueryTextChange(newText: String?): Boolean {
        binding.sneakership.visibility = View.INVISIBLE
        filterSneakers("%$newText%")
        return true
    }

    override fun onClose(): Boolean {
        binding.sneakership.visibility = View.VISIBLE
        return false
    }
}