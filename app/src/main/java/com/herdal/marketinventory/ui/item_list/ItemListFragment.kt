package com.herdal.marketinventory.ui.item_list

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.herdal.marketinventory.R
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
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteAllItems() {
        viewModel.deleteAllItems()
    }

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
    }

    private fun setupRecyclerView() = binding.apply {
        rvItems.adapter = itemListAdapter
    }

    private fun onItemClicked(id: Int) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}