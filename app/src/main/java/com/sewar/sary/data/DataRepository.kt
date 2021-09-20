package com.sewar.sary.data

import com.sewar.sary.data.models.Banner
import com.sewar.sary.data.models.Catalog
import io.reactivex.rxjava3.core.Single

interface DataRepository {
    fun banners(): Single<List<Banner>>

    fun catalogs(): Single<List<Catalog>>
}
