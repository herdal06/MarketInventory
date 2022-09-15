package com.herdal.marketinventory.ui.item_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.databinding.FragmentItemDetailBinding
import com.herdal.marketinventory.utils.extensions.getFormattedPrice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ItemDetailViewModel by viewModels()

    lateinit var item: Item

    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun bindUI(item: Item) = binding.apply {
        tvItemNameDetail.text = item.name
        tvItemPriceDetail.text = item.getFormattedPrice()
        tvItemQuantityDetail.text = item.quantityInStock.toString()
    }

    private fun getArgumentsId(): Int = navigationArgs.id

    private fun observeLiveData() {
        viewModel.retrieveItem(getArgumentsId()).observe(viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bindUI(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}