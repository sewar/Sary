package com.sewar.sary.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sewar.sary.R
import com.sewar.sary.data.models.Banner
import com.sewar.sary.databinding.BannerFragmentBinding
import com.squareup.picasso.Picasso

class BannersAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var banners: List<Banner> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = banners.size

    override fun createFragment(position: Int) = BannerFragment.newInstance(banners[position])
}

class BannerFragment : Fragment(R.layout.banner_fragment) {
    private var _binding: BannerFragmentBinding? = null
    private val binding
        get() = _binding!!


    companion object {
        private const val BANNER = "BANNER"

        fun newInstance(banner: Banner) = BannerFragment().apply {
            arguments = bundleOf(BANNER to banner)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BannerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val banner = requireArguments().getParcelable<Banner>(BANNER)!!

        binding.image.setOnClickListener {
            Toast.makeText(requireContext(), banner.link.toString(), Toast.LENGTH_SHORT).show()
        }

        Picasso.get()
            .load(banner.image)
            .fit()
            .centerCrop()
//            .placeholder()
            .into(binding.image)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
