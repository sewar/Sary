package com.sewar.sary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.sewar.sary.data.models.Catalog
import com.sewar.sary.databinding.CatalogLayoutBannersBinding
import com.sewar.sary.databinding.CatalogLayoutCatalogBinding

class CatalogsAdapter(
    private val fragment: Fragment,
    private val listener: Listener,
) : ListAdapter<CatalogsAdapterItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private const val TYPE_BANNERS = 0
        private const val TYPE_CATALOG = 1

        @JvmStatic
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CatalogsAdapterItem>() {
            override fun areItemsTheSame(
                oldItem: CatalogsAdapterItem,
                newItem: CatalogsAdapterItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CatalogsAdapterItem,
                newItem: CatalogsAdapterItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    init {
        setHasStableIds(true)
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun getItemId(position: Int) = getItem(position).id

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is CatalogsAdapterItem.Banners -> TYPE_BANNERS
        is CatalogsAdapterItem.CatalogItem -> TYPE_CATALOG
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BANNERS -> BannersViewHolder(
                CatalogLayoutBannersBinding.inflate(inflater, parent, false)
            )
            TYPE_CATALOG -> CatalogViewHolder(
                CatalogLayoutCatalogBinding.inflate(inflater, parent, false)
            )
            else -> throw RuntimeException("Item type unsupported.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannersViewHolder -> holder.bind(position)
            is CatalogViewHolder -> holder.bind(position)
        }
    }

    inner class BannersViewHolder(binding: CatalogLayoutBannersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var adapter = BannersAdapter(fragment)


        init {
            binding.viewPager.adapter = adapter
        }

        fun bind(position: Int) {
            val bannersItem = getItem(position) as CatalogsAdapterItem.Banners
            val banners = bannersItem.banners

            adapter.banners = banners
        }
    }

    inner class CatalogViewHolder(
        private val binding: CatalogLayoutCatalogBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val recyclerView = binding.recyclerView

        private var adapter: CatalogGroupsAdapter


        init {
            ViewCompat.setNestedScrollingEnabled(recyclerView, false)

            adapter = CatalogGroupsAdapter { group ->
                listener.onCatalogGroupClick(group)
            }
            recyclerView.adapter = adapter
        }

        fun bind(position: Int) {
            val catalogItem = getItem(position) as CatalogsAdapterItem.CatalogItem
            val catalog = catalogItem.catalog

            binding.title.text = catalog.title
            binding.title.isVisible = catalog.showTitle

            recyclerView.layoutManager = when (catalog.uiType) {
                Catalog.UiType.Grid -> GridLayoutManager(binding.root.context, catalog.rowCount)
                Catalog.UiType.Linear ->
                    LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
                Catalog.UiType.Slider ->
                    LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
            }

            adapter.submitList(catalog.groups)
        }
    }

    interface Listener {
        fun onCatalogGroupClick(group: Catalog.Group) {}
    }
}
