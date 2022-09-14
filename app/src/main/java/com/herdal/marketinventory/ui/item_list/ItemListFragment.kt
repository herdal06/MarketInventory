package com.herdal.marketinventory.ui.item_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.herdal.marketinventory.databinding.FragmentItemListBinding
import com.herdal.marketinventory.ui.item_list.adapter.ItemListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val itemListAdapter: ItemListAdapter by lazy {
        ItemListAdapter(::onItemClicked)
    }

    private val viewModel: ItemListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() = binding.apply {
        rvItems.adapter = itemListAdapter
    }

    private fun onItemClicked(id: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}