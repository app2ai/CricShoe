package com.example.cricshoeapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cricshoeapp.R
import com.example.cricshoeapp.ShoeApplication
import com.example.cricshoeapp.databinding.FragmentDetailsBinding
import com.example.cricshoeapp.model.Sneaker
import com.example.cricshoeapp.viewmodel.DetailsViewModel
import com.example.cricshoeapp.viewmodel.OneFailed
import com.example.cricshoeapp.viewmodel.OneInProgress
import com.example.cricshoeapp.viewmodel.OneSuccess
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.create(
            DetailsViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchShoeWithId(args.shoeId)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.sneakerData.collect {
                when (it) {
                    OneInProgress -> Toast.makeText(
                        context,
                        "Sneaker loading..",
                        Toast.LENGTH_SHORT
                    ).show()
                    OneFailed -> Toast.makeText(
                        context,
                        "Sneaker not loaded, please select again",
                        Toast.LENGTH_SHORT
                    ).show()
                    is OneSuccess -> {
                        setupData(it.shoe)
                    }
                }
            }
        }
    }

    private fun setupData(shoe: Sneaker) {
        binding.title.text = shoe.name
        binding.details.text = shoe.details
        binding.brandName.text = "Brand:  ${shoe.brand_name}"
        binding.yearOfRelease.text = "Year of Release: ${shoe.release_year}"
        binding.realPrice.text = "\$${shoe.retail_price_cents / 100}"
        binding.btnAddToCart.setOnClickListener {
            viewModel.addItemToCart(shoe.id)
            Toast.makeText(context, "Added to cart..", Toast.LENGTH_SHORT).show()
        }
        Picasso.get()
            .load(shoe.original_picture_url)
            .fit()
            .placeholder(R.drawable.baseline_downloading)
            .into(binding.sneakerImgBig)
        binding.backFromDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as ShoeApplication).appComponent.inject(this)
    }
}