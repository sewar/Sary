package com.sewar.sary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sewar.sary.R
import com.sewar.sary.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigation.setOnItemSelectedListener {
            // Don't allow selecting anything but Store tab
            if (it.itemId != R.id.store) {
                binding.bottomNavigation.post {
                    binding.bottomNavigation.selectedItemId = R.id.store
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
