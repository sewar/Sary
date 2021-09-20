package com.sewar.sary.data

import com.sewar.sary.data.models.BannersResponse
import com.sewar.sary.data.models.CatalogsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface SaryService {
    companion object {
        const val BASE_URL = "https://staging.sary.co/api/"
    }

    @GET("v2.5.1/baskets/76097/banners/")
    fun banners(): Single<BannersResponse>

    @GET("baskets/76097/catalog/")
    fun catalogs(): Single<CatalogsResponse>
}
