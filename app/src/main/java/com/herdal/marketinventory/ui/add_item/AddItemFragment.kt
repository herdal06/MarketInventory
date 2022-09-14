package com.herdal.marketinventory.ui.add_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.databinding.FragmentAddItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddItemFragment : Fragment() {

    private var _binding: FragmentAddItemBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AddItemViewModel by viewModels()

    lateinit var item: Item

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSaveButton()
    }

    private fun isEntryValid(): Boolean {
        binding.apply {
            return viewModel.isEntryValid(
                itemName = etName.text.toString(),
                itemPrice = etPrice.text.toString(),
                itemCount = etCount.text.toString()
            )
        }
    }

    private fun addNewItem() = binding.apply {
        if (isEntryValid()) {
            viewModel.addNewItem(
                itemName = etName.text.toString(),
                itemPrice = etPrice.text.toString(),
                quantity = etCount.text.toString()
            )
            navigateToListFragment()
        }
    }

    private fun navigateToListFragment() {
        val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
        findNavController().navigate(action)
    }

    private fun clickSaveButton() {
        binding.buttonSaveAction.setOnClickListener {
            addNewItem()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}