package com.herdal.marketinventory.ui.item_list

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.herdal.marketinventory.R
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.databinding.FragmentItemListBinding
import com.herdal.marketinventory.ui.item_list.adapter.ItemListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentItemListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var item: Item

    private val itemListAdapter: ItemListAdapter by lazy {
        ItemListAdapter(::onItemClicked)
    }

    private val viewModel: ItemListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeLiveData()
        fabOnClick()
        setupTopBarMenu()
        swipeToDelete()
    }

    private fun swipeToDelete() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = itemListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.onItemSwiped(item)
            }
        }).attachToRecyclerView(binding.rvItems)
    }

    private fun setupTopBarMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.topbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_delete_all -> {
                        showAlertDialog()
                        true
                    }
                    R.id.menu_search_item -> {
                        //searchByName("")
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteAllItems() {
        viewModel.deleteAllItems()
    }

    // Confirm deletion alert dialog
    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.delete_question)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteAllItems()
            }.setNegativeButton(getString(R.string.no)) { _, _ ->

            }
            .show()
    }

    private fun fabOnClick() = binding.fabAddItem.setOnClickListener {
        val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
            //getString(R.string.add_fragment_title)
        )
        this.findNavController().navigate(action)
    }

    private fun observeLiveData() {
        viewModel.allItems.observe(viewLifecycleOwner) { items ->
            items.let {
                itemListAdapter.submitList(it)
            }
        }
        collectEvents()
    }

    private fun collectEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.itemsEvent.collect { event ->
                when (event) {
                    is ItemListViewModel.ItemsEvent.ShowUndoDeleteItemMessage -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.item_deleted),
                            Snackbar.LENGTH_LONG
                        )
                            .setAction(getString(R.string.undo)) {
                                viewModel.onUndoDeleteClick(event.item)
                            }.show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.apply {
        rvItems.adapter = itemListAdapter
    }

    private fun onItemClicked(id: Int) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchByName(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchByName(query)
        }
        return true
    }

    private fun searchByName(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchByName(searchQuery).observe(this) { list ->
            list.let { itemListAdapter.submitList(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}