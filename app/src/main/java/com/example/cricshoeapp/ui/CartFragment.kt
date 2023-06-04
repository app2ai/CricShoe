package com.example.cricshoeapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricshoeapp.R
import com.example.cricshoeapp.ShoeApplication
import com.example.cricshoeapp.databinding.FragmentCartBinding
import com.example.cricshoeapp.utils.ShoeCartItemListener
import com.example.cricshoeapp.viewmodel.CartEmpty
import com.example.cricshoeapp.viewmodel.CartFailed
import com.example.cricshoeapp.viewmodel.CartInProgress
import com.example.cricshoeapp.viewmodel.CartSuccess
import com.example.cricshoeapp.viewmodel.CartViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartFragment : Fragment(), ShoeCartItemListener {
    private lateinit var binding: FragmentCartBinding
    private val mAdapter: CartAdapter by lazy {
        CartAdapter(this)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.create(
            CartViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCartShoeFromDb()

        with(binding.cartRecyclerView){
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = mAdapter
            setHasFixedSize(true)
        }

        binding.backFromCart.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.taxCharges.text = getString(R.string.tx_chrgs)
        binding.btnCheckOut.setOnClickListener {
            Toast.makeText(context, "This feature is not available yet..", Toast.LENGTH_SHORT).show()
        }
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartSneakerData.collect{
                    when(it) {
                        CartFailed -> {
                            with(binding) {
                                cartProgressBar.visibility = View.GONE
                                emptyData.visibility = View.VISIBLE
                                cartRecyclerView.visibility = View.GONE
                            }
                            Toast.makeText(context, "Oops, unable to load data. Please Retry", Toast.LENGTH_LONG).show()
                        }
                        CartInProgress -> {
                            with(binding) {
                                cartProgressBar.visibility = View.VISIBLE
                                cartRecyclerView.visibility = View.GONE
                                emptyData.visibility = View.GONE
                            }
                        }
                        is CartSuccess -> {
                            with(binding) {
                                cartProgressBar.visibility = View.GONE
                                cartRecyclerView.visibility = View.VISIBLE
                                emptyData.visibility = View.GONE
                            }
                            mAdapter.differCart.submitList(it.shoes)
                        }
                        CartEmpty -> {
                            with(binding) {
                                cartProgressBar.visibility = View.GONE
                                cartRecyclerView.visibility = View.GONE
                                emptyData.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.subtotalLD.collect { subtotal ->
                    binding.subTotal.text = getString(R.string.cart_subtotal, subtotal)
                    binding.btnCheckOut.isEnabled = subtotal > 0
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.totalLD.collect { total ->
                    binding.totalPrice.text = getString(R.string.cart_total, total)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as ShoeApplication).appComponent.inject(this)
    }

    override fun clickToRemoveItem(sId: Int) {
        viewModel.removeItemToCart(sId)
    }
}