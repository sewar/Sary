package com.sewar.sary.data

import com.sewar.sary.data.models.Banner
import com.sewar.sary.data.models.BannersResponse
import com.sewar.sary.data.models.Catalog
import com.sewar.sary.data.models.CatalogsResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ApiDataRepository @Inject constructor(
    private val saryService: SaryService,
) : DataRepository {
    override fun banners(): Single<List<Banner>> {
        return saryService.banners()
            .map(BannersResponse::toBanners)
    }

    override fun catalogs(): Single<List<Catalog>> {
        return saryService.catalogs()
            .map(CatalogsResponse::toCatalogs)
    }
}
