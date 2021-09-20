package com.sewar.sary.ui

import com.sewar.sary.AdapterIdUtils
import com.sewar.sary.data.models.Banner
import com.sewar.sary.data.models.Catalog

sealed class CatalogsAdapterItem {
    abstract val id: Long

    data class Banners(val banners: List<Banner>) : CatalogsAdapterItem() {
        override val id = AdapterIdUtils.id("banners")
    }

    data class CatalogItem(val catalog: Catalog) : CatalogsAdapterItem() {
        override val id = AdapterIdUtils.id("catalog", catalog.id.toLong())
    }
}
