package com.sewar.sary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sewar.sary.AdapterIdUtils
import com.sewar.sary.data.models.Catalog
import com.sewar.sary.databinding.CatalogLayoutGroupBannerBinding
import com.sewar.sary.databinding.CatalogLayoutGroupGroupBinding
import com.sewar.sary.databinding.CatalogLayoutGroupSmartBinding
import com.squareup.picasso.Picasso

class CatalogGroupsAdapter(private val listener: (Catalog.Group) -> Unit) :
    ListAdapter<Catalog.Group, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private const val TYPE_SMART = 0
        private const val TYPE_GROUP = 1
        private const val TYPE_BANNER = 2

        @JvmStatic
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Catalog.Group>() {
            override fun areItemsTheSame(
                oldItem: Catalog.Group,
                newItem: Catalog.Group,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Catalog.Group,
                newItem: Catalog.Group,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    init {
        setHasStableIds(true)
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun getItemId(position: Int) = AdapterIdUtils.id(
        getItem(position).image.toString(),
        getItem(position).id.toLong(),
    )

    override fun getItemViewType(position: Int): Int = when (getItem(position).dataType) {
        Catalog.DataType.Smart -> TYPE_SMART
        Catalog.DataType.Group -> TYPE_GROUP
        Catalog.DataType.Banner -> TYPE_BANNER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_SMART -> SmartViewHolder(
                CatalogLayoutGroupSmartBinding.inflate(inflater, parent, false)
            )
            TYPE_GROUP -> GroupViewHolder(
                CatalogLayoutGroupGroupBinding.inflate(inflater, parent, false)
            )
            TYPE_BANNER -> BannerViewHolder(
                CatalogLayoutGroupBannerBinding.inflate(inflater, parent, false)
            )
            else -> throw RuntimeException("Item type unsupported.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SmartViewHolder -> holder.bind(position)
            is GroupViewHolder -> holder.bind(position)
            is BannerViewHolder -> holder.bind(position)
        }
    }

    inner class SmartViewHolder(private val binding: CatalogLayoutGroupSmartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(position: Int) {
            val group = getItem(position)

            Picasso.get()
                .load(group.image)
                .fit()
                .centerInside()
                //.placeholder()
                .into(binding.image)

            binding.name.text = group.name
        }
    }

    inner class GroupViewHolder(private val binding: CatalogLayoutGroupGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val screenWidth = binding.root.resources.displayMetrics.widthPixels


        init {
            binding.root.setOnClickListener {
                listener.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(position: Int) {
            val group = getItem(position)

            Picasso.get()
                .load(group.image)
//                .fit()
                .resize(screenWidth / group.rowCount, 0)
                .centerInside()
                //.placeholder()
                .into(binding.image)
        }
    }

    inner class BannerViewHolder(private val binding: CatalogLayoutGroupBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val screenWidth = binding.root.resources.displayMetrics.widthPixels


        init {
            binding.root.setOnClickListener {
                listener.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(position: Int) {
            val group = getItem(position)

            Picasso.get()
                .load(group.image)
//                .fit()
                .resize(screenWidth / group.rowCount, 0)
                .centerInside()
                //.placeholder()
                .into(binding.image)
        }
    }
}
