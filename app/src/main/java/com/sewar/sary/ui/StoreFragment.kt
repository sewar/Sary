package com.sewar.sary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sewar.sary.data.DataRepository
import com.sewar.sary.data.models.Catalog
import com.sewar.sary.databinding.StoreFragmentBinding
import javax.inject.Inject

class StoreFragment : Fragment(), CatalogsAdapter.Listener {
    private var _binding: StoreFragmentBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var dataRepository: DataRepository

    private val viewModel by viewModels<StoreViewModel>(factoryProducer = {
        object : AbstractSavedStateViewModelFactory(this, null) {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return StoreViewModel(handle, dataRepository) as T
            }
        }
    })

    private var adapter: CatalogsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as BaseActivity)
            .activityComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StoreFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addressText.text = "Address"

        binding.cartButton.setOnClickListener { }

        adapter = CatalogsAdapter(this, this)
        binding.recyclerView.adapter = adapter

        viewModel.storeState().observe(viewLifecycleOwner, {
            when (it) {
                StoreState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerView.isVisible = false
                }
                is StoreState.Content -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerView.isVisible = true
                    adapter?.submitList(it.adapterItems)
                }
            }
        })

        viewModel.errorMessage().observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.onErrorMessageHandled()
            }
        })
    }

    override fun onDestroyView() {
        adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onCatalogGroupClick(group: Catalog.Group) {
        if (group.name.isNotBlank()) {
            Toast.makeText(requireContext(), group.name, Toast.LENGTH_SHORT).show()
        } else if (group.deepLink != null) {
            Toast.makeText(requireContext(), group.deepLink.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
